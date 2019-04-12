import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

configure<KotlinMultiplatformExtension> {
    sourceSets {
        forEach {
            it.languageSettings.apply {
                useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
            }
        }
    }
}