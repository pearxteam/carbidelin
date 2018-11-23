import ru.pearx.multigradle.plugin.modular.MultiGradleModularSettings
import ru.pearx.multigradle.util.kotlinDev

// Scary things happen here...

val multigradleVersion: String by settings
val kotlinVersion: String by settings

buildscript {
    val multigradleVersion: String by settings
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("ru.pearx.multigradle:multigradle:$multigradleVersion")
    }
}

rootProject.name = "carbidelin"

apply<MultiGradleModularSettings>()

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-gradle-plugin")
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
            if (requested.id.id == "ru.pearx.multigradle.modular.project")
                useVersion(multigradleVersion)
        }
    }
}