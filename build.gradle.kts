import ru.pearx.multigradle.util.MultiGradleExtension
import ru.pearx.multigradle.util.subplatforms

val devBuildNumber: String? by project

plugins {
    id("ru.pearx.multigradle.modular.project")
    id("kotlin-gradle-plugin") apply (false)
}

subplatforms {
    group = "ru.pearx.carbidelin"

    apply<MavenPublishPlugin>()

    configure<MultiGradleExtension> {
        if(devBuildNumber != null) {
            projectVersion = "$projectVersion-dev-$devBuildNumber"
        }
        kotlinExperimentalFeatures.add("kotlin.ExperimentalUnsignedTypes")
    }

    configure<PublishingExtension> {
        repositories {
            fun AuthenticationSupported.pearxCredentials() {
                credentials {
                    username = properties["pearxRepoUsername"].toString()
                    password = properties["pearxRepoPassword"].toString()
                }
            }
            maven {
                pearxCredentials()
                name = "develop"
                url = uri("https://repo.pearx.ru/maven2/develop/")
            }
            maven {
                pearxCredentials()
                name = "release"
                url = uri("https://repo.pearx.ru/maven2/release/")
            }
        }
        publications {
            register<MavenPublication>("maven") {
                from(components["java"])
            }
        }
    }

    tasks {
        register("publishDevelop") {
            group = "publishing"
            dependsOn(withType<PublishToMavenRepository>().matching { it.repository == project.the<PublishingExtension>().repositories["develop"] })
        }
        register("publishRelease") {
            group = "publishing"
            dependsOn(withType<PublishToMavenRepository>().matching { it.repository == project.the<PublishingExtension>().repositories["release"] })
        }
    }
}
