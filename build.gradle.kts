import ru.pearx.multigradle.plugin.modular.MultiGradleModularProject
import ru.pearx.multigradle.util.MultiGradleExtension
import ru.pearx.multigradle.util.PLATFORM_JS
import ru.pearx.multigradle.util.subplatforms
import ru.pearx.multigradle.util.kotlinDev

plugins {
    id("ru.pearx.multigradle.modular.project")
    id("kotlin-gradle-plugin") apply(false)
}

subplatforms {
    group = "ru.pearx.carbidelin"

    apply<MavenPublishPlugin>()
    apply<SigningPlugin>()

    configure<MultiGradleExtension> {
        kotlinExperimentalFeatures.add("kotlin.ExperimentalUnsignedTypes")
    }

    configure<PublishingExtension> {
        publications {
            register<MavenPublication>("maven") {
                from(components["java"])
            }
        }
    }

    configure<SigningExtension> {
        sign(the<PublishingExtension>().publications["maven"])
    }
}