import java.io.FileReader
import java.util.Properties

plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    gradlePluginPortal()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-dev/")
    }
}

val properties = Properties().apply { FileReader(File(rootDir.parent, "project.properties")).use { load(it) } }
fun dep(base: String, versionName: String) = "$base:${properties[versionName] as String}"

dependencies {
    "compile"(dep("org.jetbrains.kotlin:kotlin-gradle-plugin", "kotlinVersion"))
    "compile"(dep("com.moowork.node:com.moowork.node.gradle.plugin", "nodejsPluginVersion"))
}