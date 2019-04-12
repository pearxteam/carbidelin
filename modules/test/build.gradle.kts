import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

configure<KotlinMultiplatformExtension> {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(kotlin("test-common"))
            }
        }

        named("jsMain") {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        named("jvmMain") {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}