@file:OptIn(ExperimentalWasmDsl::class) @file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.zip.ZipFile

plugins {
	id("com.android.kotlin.multiplatform.library")
	kotlin("multiplatform")
	kotlin("plugin.compose")
	`maven-publish`
	id("com.palantir.git-version")
}

val iconsNamespace = "work.niggergo.yesui.icons"
val iconsObjName = "YesIcons"

val lucideObjName = "Lucide"
val lucidePkg = "com.composables.icons.lucide"

val (generatedCommonMain, generatedAndroidMain, generatedNonAndroidMain) = layout.buildDirectory.dir("generated/yesui/icons")
	.run {
		Triple(
			map { it.dir("commonMain/kotlin") },
			map { it.dir("androidMain/kotlin") },
			map { it.dir("nonAndroidMain/kotlin") })
	}

val scanDep by configurations.creating {
	isCanBeConsumed = false
	isCanBeResolved = true
	isTransitive = false
}
dependencies { scanDep(libs.lucideAndroid) }

data class IconInfo(val resName: String, val propName: String)

fun scanIconsFromAar(aar: File) = ZipFile(aar).use { zip ->
	zip.entries().asSequence().map { it.name }.filter { it.startsWith("res/drawable/") && it.endsWith(".xml") }
		.map { it.substringAfterLast('/').removeSuffix(".xml") }.filter { it.startsWith("lucide_") }.distinct().sorted()
		.toList()
}.map { resName ->
	IconInfo(
		resName,
		resName.removePrefix("lucide_ic_").split('_')
			.joinToString("") { part -> part.replaceFirstChar { ch -> ch.titlecase() } })
}

val suppress = "@file:Suppress(\"PackageDirectoryMismatch\", \"unused\")"

fun writeCommonFile(outputDir: File, icons: List<IconInfo>) = outputDir.apply {
	mkdirs()
	File(this, "$iconsObjName.common.kt").writeText(
		buildString {
			appendLine(suppress)
			appendLine()
			appendLine("package $iconsNamespace")
			appendLine()
			appendLine("import androidx.compose.runtime.Composable")
			appendLine("import androidx.compose.ui.graphics.painter.Painter")
			appendLine()
			icons.forEach { icon ->
				appendLine("@get:Composable expect val $iconsObjName.${icon.propName}: Painter")
			}
		})
}

fun writeAndroidFile(outputDir: File, icons: List<IconInfo>) = outputDir.apply {
	mkdirs()
	File(this, "$iconsObjName.android.kt").writeText(
		buildString {
			appendLine(suppress)
			appendLine()
			appendLine("package $iconsNamespace")
			appendLine()
			appendLine("import androidx.compose.runtime.Composable")
			appendLine("import androidx.compose.ui.graphics.painter.Painter")
			appendLine("import androidx.compose.ui.res.painterResource")
			appendLine()
			appendLine("import $lucidePkg.R")
			appendLine()
			icons.forEach { icon ->
				appendLine("@get:Composable actual val $iconsObjName.${icon.propName} get(): Painter = painterResource(R.drawable.${icon.resName})")
			}
		})
}

fun writeNonAndroidFile(outputDir: File, icons: List<IconInfo>) = outputDir.apply {
	mkdirs()
	File(this, "$iconsObjName.nonAndroid.kt").writeText(
		buildString {
			appendLine(suppress)
			appendLine()
			appendLine("package $iconsNamespace")
			appendLine()
			appendLine("import androidx.compose.runtime.Composable")
			appendLine("import androidx.compose.ui.graphics.painter.Painter")
			appendLine("import androidx.compose.ui.graphics.vector.rememberVectorPainter")
			appendLine()
			appendLine("import $lucidePkg.*")
			appendLine()
			icons.forEach { icon ->
				appendLine("@get:Composable actual val $iconsObjName.${icon.propName} get(): Painter = rememberVectorPainter($lucideObjName.${icon.propName})")
			}
		})
}

@Suppress("TaskMissingDescription") val generateLucideIcons by tasks.registering {
	notCompatibleWithConfigurationCache("build.gradle.kts")
	inputs.files(scanDep).withPropertyName("inputArtifacts").withPathSensitivity(PathSensitivity.RELATIVE)
	outputs.dir(generatedCommonMain).withPropertyName("generatedCommonMain")
	outputs.dir(generatedAndroidMain).withPropertyName("generatedAndroidMain")
	outputs.dir(generatedNonAndroidMain).withPropertyName("generatedNonAndroidMain")
	doLast {
		val icons = scanIconsFromAar(scanDep.singleFile)
		writeCommonFile(generatedCommonMain.get().asFile, icons)
		writeAndroidFile(generatedAndroidMain.get().asFile, icons)
		writeNonAndroidFile(generatedNonAndroidMain.get().asFile, icons)
	}
}

val generatedCommonMainSources = files(generatedCommonMain).builtBy(generateLucideIcons)
val generatedAndroidMainSources = files(generatedAndroidMain).builtBy(generateLucideIcons)
val generatedNonAndroidMainSources = files(generatedNonAndroidMain).builtBy(generateLucideIcons)

kotlin {
	applyDefaultHierarchyTemplate()
	withSourcesJar()

	android {
		namespace = iconsNamespace
		compileSdk = libs.versions.compileSdk.get().toInt()
		minSdk = 1
		buildToolsVersion = libs.versions.buildTools.get()

		compilerOptions.jvmTarget = JvmTarget.JVM_1_8

		optimization {
			consumerKeepRules.publish = true
			consumerKeepRules.files("consumer-rules.pro")
			minify = false
		}
	}

	jvm { compilerOptions.jvmTarget = JvmTarget.JVM_1_8 }

	iosArm64()
	iosSimulatorArm64()

	js(IR) { browser() }
	wasmJs { browser() }

	sourceSets {
		val commonMain by getting {
			kotlin.srcDir(generatedCommonMainSources)
			dependencies {
				implementation(libs.composeUi)
				implementation(libs.composeRuntime)
			}
		}
		androidMain {
			kotlin.srcDir(generatedAndroidMainSources)
			dependencies { implementation(libs.lucideAndroid) }
		}
		val nonAndroidMain by creating {
			dependsOn(commonMain)
			kotlin.srcDir(generatedNonAndroidMainSources)
			dependencies { implementation(libs.lucideMultiplatform) }
		}
		jvmMain { dependsOn(nonAndroidMain) }
		appleMain { dependsOn(nonAndroidMain) }
		webMain { dependsOn(nonAndroidMain) }
	}
}

tasks.matching { it.name == "prepareKotlinIdeaImport" }.configureEach {
	dependsOn(generateLucideIcons)
}
afterEvaluate {
	publishing {
		publications { withType<MavenPublication>(configurePublishConfig("Icons")) }
		repositories { mavenLocal() }
	}
}