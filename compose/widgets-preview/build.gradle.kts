import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
	kotlin("multiplatform")
	kotlin("plugin.compose")
	id("org.jetbrains.compose")
}

compose.resources {
	packageOfResClass = "work.niggergo.yesui.preview.generated.resources"
}

kotlin {
	applyDefaultHierarchyTemplate()

	@OptIn(ExperimentalWasmDsl::class) wasmJs {
		browser {
			commonWebpackConfig {
				outputFileName = "yesui-preview.js"
			}
		}
		binaries.executable()
	}

	sourceSets.webMain.dependencies {
		implementation(project(":yesui:core"))
		implementation(project(":yesui:widgets"))
		implementation(project(":yesui:ultra"))
		implementation(project(":yesui:icons"))

		implementation(libs.composeFoundation)
		implementation(libs.composeResources)
		implementation(libs.composeRuntime)
		implementation(libs.composeUi)
	}
}