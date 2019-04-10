import ru.pearx.multigradle.util.MultiGradleExtension

val devBuildNumber: String? by project

plugins {
    id("ru.pearx.multigradle.modular.project")
    id("kotlin-gradle-plugin") apply (false)
}

project("modules").subprojects {
    group = "ru.pearx.carbidelin"

    apply<MavenPublishPlugin>()

    configure<MultiGradleExtension> {
        if(devBuildNumber != null) {
            projectVersion = "$projectVersion-dev-$devBuildNumber"
        }
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
