import java.io.FileReader
import java.util.Properties

plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    gradlePluginPortal()
}

val properties = Properties().apply { FileReader(File(rootDir.parent, "project.properties")).use { load(it) } }
dependencies {
    "compile"("org.jetbrains.kotlin:kotlin-gradle-plugin:${properties["kotlinVersion"] as String}")
    "compile"("com.moowork.node:com.moowork.node.gradle.plugin:${properties["nodejsPluginVersion"] as String}")
}