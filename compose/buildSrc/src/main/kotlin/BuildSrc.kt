import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*

fun Project.configurePublishConfig(nameExt: String = ""): MavenPublication.() -> Unit = {
	groupId = "work.niggergo.yesui"
	val versionDetails: Closure<VersionDetails> by extra
	version = versionDetails().lastTag ?: "0.0"

	pom {
		name = "NGA YumeStarUI${if (nameExt.isNotEmpty()) " $nameExt" else ""}"
		description = "YumeStarUI (a.k.a. YesUI), NGA series UI library."
		url = "https://app.niggergo.work/yesui/"

		licenses {
			license {
				name = "F2DLPRL"
				url = "https://license.fileto.download/LICENSE.txt"
				distribution = "repo"
			}
		}

		developers {
			developer {
				id = "shirorren"
				name = "ShIroRRen"
				email = "shiro@oom-wg.dev"
				url = "https://shiror.ren"
			}
		}

		organization {
			name = "OOM WG"
			url = "https://oom-wg.dev"
		}

		scm {
			connection = "scm:git:https://github.com/OOM-WG/YumeStarUI.git"
			developerConnection = "scm:git:https://github.com/OOM-WG/YumeStarUI.git"
			url = "https://github.com/OOM-WG/YumeStarUI.git"
		}
	}

	when (name) {
		"androidRelease" -> "releaseRuntimeClasspath"
		else             -> listOf(
			"${name}RuntimeClasspath", "${name}CompileKlibraries"
		).firstOrNull { project.configurations.findByName(it) != null }
	}?.let {
		versionMapping { allVariants { fromResolutionOf(it) } }
	}
}