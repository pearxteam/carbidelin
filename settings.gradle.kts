import ru.pearx.multigradle.plugin.modular.MultiGradleModularSettings

// Scary things happen here...

val multigradleVersion: String by settings
val kotlinVersion: String by settings

buildscript {
    val multigradleVersion: String by settings
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://repo.pearx.ru/maven2/develop/")}
    }
    dependencies {
        classpath("ru.pearx.multigradle:multigradle:$multigradleVersion")
    }
}

rootProject.name = "carbidelin"

apply<MultiGradleModularSettings>()

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://repo.pearx.ru/maven2/develop/")}
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-gradle-plugin")
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
            if (requested.id.id == "ru.pearx.multigradle.modular.project")
                useVersion(multigradleVersion)
        }
    }
}