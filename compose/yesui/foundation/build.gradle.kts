@file:OptIn(ExperimentalWasmDsl::class) @file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("com.android.kotlin.multiplatform.library")
	kotlin("multiplatform")
	kotlin("plugin.compose")
	`maven-publish`
	id("com.palantir.git-version")
}

kotlin {
	applyDefaultHierarchyTemplate()
	withSourcesJar()

	android {
		namespace = "work.niggergo.yesui.foundation"
		compileSdk = libs.versions.compileSdk.get().toInt()
		minSdk = 1
		buildToolsVersion = libs.versions.buildTools.get()

		compilerOptions.jvmTarget = JvmTarget.JVM_17

		optimization {
			consumerKeepRules.publish = true
			consumerKeepRules.files("consumer-rules.pro")
			minify = false
		}
	}

	jvm { compilerOptions.jvmTarget = JvmTarget.JVM_11 }

	iosArm64()
	iosSimulatorArm64()

	js(IR) { browser() }
	wasmJs { browser() }

	sourceSets {
		commonMain.dependencies {
			implementation(libs.composeFoundation)
			implementation(libs.composeMaterialRipple)
			implementation(libs.composeRuntime)
			implementation(libs.composeUi)

			implementation(libs.unstyledPrimitives)
			implementation(libs.unstyledTheme)
		}
		androidMain.dependencies {
			implementation(libs.systemExtension)
		}
		webMain.dependencies {
			implementation(libs.kotlinxBrowser)
		}
	}
}

afterEvaluate {
	publishing {
		publications { withType<MavenPublication>(configurePublishConfig("Foundation")) }
		repositories { mavenLocal() }
	}
}