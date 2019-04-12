import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

configure<KotlinMultiplatformExtension> {
    sourceSets {
        forEach {
            it.languageSettings.apply {
                useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
                enableLanguageFeature("InlineClasses")
            }
        }
        named("commonTest") {
            dependencies {
                api(project(":modules:carbidelin-test"))
            }
        }
        named("jvmTest") {
            dependencies {
                api(project(":modules:carbidelin-test"))
            }
        }
        named("jsTest") {
            dependencies {
                api(project(":modules:carbidelin-test"))
            }
        }
    }
}