import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure

plugins {
	`java-platform`
	`maven-publish`
	id("com.palantir.git-version")
}

val versionDetails: Closure<VersionDetails> by extra
group = "work.niggergo.yesui"
version = versionDetails().lastTag

javaPlatform { allowDependencies() }

dependencies {
	constraints {
		api(project(":yesui:foundation"))
		api(project(":yesui:components"))
		api(project(":yesui:patterns"))
		api(project(":yesui:icons"))
	}
}

afterEvaluate {
	publishing {
		publications {
			create<MavenPublication>("maven") {
				from(components["javaPlatform"])
				configurePublishConfig("BOM")()
			}
		}
		repositories { mavenLocal() }
	}
}