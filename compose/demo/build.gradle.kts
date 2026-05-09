import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	kotlin("multiplatform")
	kotlin("plugin.compose")
	id("org.jetbrains.compose")
	id("org.jetbrains.compose.hot-reload")
}

compose.resources {
	packageOfResClass = "work.niggergo.yesui.demo.generated.resources"
}

kotlin {
	applyDefaultHierarchyTemplate()

	jvm { compilerOptions.jvmTarget = JvmTarget.JVM_17 }

	@OptIn(ExperimentalWasmDsl::class) wasmJs {
		browser {
			commonWebpackConfig {
				outputFileName = "yesui-demo.js"
			}
		}
		binaries.executable()
	}

	sourceSets {
		commonMain.dependencies {
			implementation(project(":yesui:foundation"))
			implementation(project(":yesui:components"))
			implementation(project(":yesui:patterns"))
			implementation(project(":yesui:icons"))

			implementation(libs.composeFoundation)
			implementation(libs.composeResources)
			implementation(libs.composeRuntime)
			implementation(libs.composeUi)
		}

		jvmMain.dependencies {
			implementation(compose.desktop.currentOs)
		}
	}
}