package ru.pearx.carbidelin.gradle.util

import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.NodePlugin
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.BasePluginConvention
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformCommonPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJsPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


/*
 * Created by mrAppleXZ on 01.09.18.
 */
enum class Platform(val codeName: String)
{
    //todo Native support
    COMMON("common")
    {
        override fun applyProject(platform: Project, module: Project, root: Project, properties: CarbidelinProperties)
        {
            super.applyProject(platform, module, root, properties)

            with(platform) {
                apply<KotlinPlatformCommonPlugin>()

                dependencies {
                    "compile"(kotlin("stdlib-common"))

                    "testCompile"(kotlin("test-common"))
                    "testCompile"(kotlin("test-annotations-common"))
                }
            }
        }
    },
    JS("js")
    {
        override fun applyProject(platform: Project, module: Project, root: Project, properties: CarbidelinProperties)
        {
            super.applyProject(platform, module, root, properties)

            with(platform) {
                apply<KotlinPlatformJsPlugin>()
                apply<NodePlugin>()

                configure<NodeExtension> {
                    version = properties.nodejsVersion
                    npmVersion = properties.npmVersion
                    download = true
                }

                dependencies {
                    "expectedBy"(module.project("common"))

                    "compile"(kotlin("stdlib-js"))

                    "testCompile"(kotlin("test-js"))
                }

                tasks {
                    withType<Kotlin2JsCompile> {
                        kotlinOptions.moduleKind = "umd"
                    }
                    create<Sync>("syncNodeModules") {
                        dependsOn("compileKotlin2Js")
                        doFirst {
                            from(the<SourceSetContainer>()["main"].output)
                            configurations["testCompile"].forEach { from(zipTree(it)) }
                            include { it.path.endsWith(".js", true) }
                            into("$buildDir/node_modules")
                        }
                    }
                    create<NpmTask>("installMocha") {
                        setArgs(listOf("install", "mocha@${properties.mochaVersion}"))
                    }
                    val compileTest = getByName<Kotlin2JsCompile>("compileTestKotlin2Js")
                    create<NodeTask>("runMocha") {
                        dependsOn("installMocha", "syncNodeModules", "compileTestKotlin2Js")
                        setScript(file("node_modules/mocha/bin/mocha"))
                        setArgs(listOf(compileTest.destinationDir))
                    }
                    named<Test>("test") {
                        dependsOn("runMocha")
                    }
                }
            }
        }
    },
    JVM("jvm")
    {
        override fun applyProject(platform: Project, module: Project, root: Project, properties: CarbidelinProperties)
        {
            super.applyProject(platform, module, root, properties)

            with(platform) {
                apply<KotlinPlatformJvmPlugin>()
                apply<JacocoPlugin>()

                configure<JavaPluginConvention> {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                }

                configure<JacocoPluginExtension> {
                    toolVersion = properties.jacocoVersion
                }

                dependencies {
                    "expectedBy"(module.project("common"))

                    "compile"(kotlin("stdlib-jdk8"))

                    "testCompile"(kotlin("test-annotations-common"))
                    "testCompile"(kotlin("test-junit5"))
                    "testCompile"("org.junit.jupiter:junit-jupiter-api:${properties.junitJupiterVersion}")
                    "testRuntime"("org.junit.jupiter:junit-jupiter-engine:${properties.junitJupiterVersion}")
                }

                tasks {
                    withType<KotlinCompile> {
                        kotlinOptions.jvmTarget = "1.8"
                        kotlinOptions.freeCompilerArgs = listOf("-Xno-param-assertions")
                    }
                    named<Test>("test") {
                        useJUnitPlatform()
                    }
                }
            }
        }
    };

    open fun applyProject(platform: Project, module: Project, root: Project, properties: CarbidelinProperties)
    {
        with(platform) {
            apply<BasePlugin>()

            repositories {
                mavenCentral()
            }

            configure<BasePluginConvention> {
                //carbidelin-core-jvm
                archivesBaseName = "${root.name}-${module.name}-$codeName"
            }
        }
    }

    companion object
    {
        fun valueOfCodeName(codeName: String) = values().firstOrNull { it.codeName == codeName }
                ?: throw IllegalArgumentException("The platform of code name '$codeName' doesn't exist!")
    }
}