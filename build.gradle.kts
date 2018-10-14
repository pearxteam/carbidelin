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
    repositories {
        kotlinDev()
    }
    configure<MultiGradleExtension> {
        kotlinExperimentalFeatures.add("kotlin.ExperimentalUnsignedTypes")
    }
}