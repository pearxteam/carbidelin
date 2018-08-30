import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.NodePlugin
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformCommonPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJsPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-platform-common") apply(false)
    id("kotlin-platform-js") apply(false)
    id("com.moowork.node") apply(false)
    id("kotlin-platform-jvm") apply(false)
}

val project_name: String by project
val project_platforms: String by project
val platforms = project_platforms.split(',')

subprojects {
    if(name in platforms)
    {
        apply<BasePlugin>()

        repositories {
            mavenCentral()
        }

        configure<BasePluginConvention> {
            archivesBaseName = "$project_name-${parent!!.name}"
        }

        when(name)
        {
            "common" ->
            {
                apply<KotlinPlatformCommonPlugin>()

                dependencies {
                    "compile"(kotlin("stdlib-common"))

                    "testCompile"(kotlin("test-common"))
                    "testCompile"(kotlin("test-annotations-common"))
                }
            }
            "js" ->
            {
                val nodejs_version: String by project
                val npm_version: String by project
                val mocha_version: String by project

                apply<KotlinPlatformJsPlugin>()
                apply<NodePlugin>()

                configure<NodeExtension> {
                    version = nodejs_version
                    npmVersion = npm_version
                    download = true
                }

                dependencies {
                    "expectedBy"(parent!!.project("common"))

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
                        setArgs(listOf("install", "mocha@$mocha_version"))
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
            "jvm" ->
            {
                val junit_jupiter_version: String by project
                val jacoco_version: String by project

                apply<KotlinPlatformJvmPlugin>()
                apply(plugin = "jacoco")

                configure<JavaPluginConvention> {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                }

                configure<JacocoPluginExtension> {
                    toolVersion = jacoco_version
                }

                dependencies {
                    "expectedBy"(parent!!.project("common"))

                    "compile"(kotlin("stdlib-jdk8"))

                    "testCompile"(kotlin("test-annotations-common"))
                    "testCompile"(kotlin("test-junit5"))
                    "testCompile"("org.junit.jupiter:junit-jupiter-api:$junit_jupiter_version")
                    "testRuntime"("org.junit.jupiter:junit-jupiter-engine:$junit_jupiter_version")
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
    }
}