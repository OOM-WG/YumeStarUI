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
		namespace = "work.niggergo.yesui.ultra"
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

	jvm { compilerOptions.jvmTarget = JvmTarget.JVM_11 }

	iosArm64()
	iosSimulatorArm64()

	js(IR) { browser() }
	wasmJs { browser() }

	sourceSets {
		commonMain.dependencies {
			api(project(":yesui:core"))
			api(project(":yesui:widgets"))

			api(libs.composeFoundation)
			api(libs.composeUi)
			api(libs.composeRuntime)
		}
	}
}

afterEvaluate {
	publishing {
		publications { withType<MavenPublication>(configurePublishConfig("Ultra")) }
		repositories { mavenLocal() }
	}
}