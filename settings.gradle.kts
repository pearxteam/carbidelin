val project_name: String by settings
val project_platforms: String by settings
val nodejs_plugin_version: String by settings
val kotlin_version: String by settings

val platforms = project_platforms.split(',')

rootProject.name = project_name

for(module in file("modules").listFiles())
{
    if(module.isDirectory)
    {
        for(platform in platforms)
        {
            include(":modules:${module.name}:$platform")
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if(requested.id.id == "com.moowork.node")
                useVersion(nodejs_plugin_version)
            if(requested.id.id.startsWith("kotlin-platform-"))
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        }
    }
}