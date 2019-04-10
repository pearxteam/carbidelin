import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import ru.pearx.multigradle.util.MultiGradleExtension

val kotlinxIoVersion: String by project
val textEncodingVersion: String by project

configure<KotlinMultiplatformExtension> {
    sourceSets {
        named("commonMain") {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-io:$kotlinxIoVersion")
                api(project(":modules:carbidelin-core"))
            }
        }
        named("jvmMain") {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-io-jvm:$kotlinxIoVersion")
                api(project(":modules:carbidelin-core"))
            }
        }
        named("jsMain") {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-io-js:$kotlinxIoVersion")
                api(project(":modules:carbidelin-core"))
            }
        }
    }
}

configure<MultiGradleExtension> {
    npmPackages {
        put("text-encoding", textEncodingVersion)
    }
}