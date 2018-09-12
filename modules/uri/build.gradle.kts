import ru.pearx.carbidelin.gradle.util.multiplatformDependencies
import ru.pearx.carbidelin.gradle.util.Platform

multiplatformDependencies {
    "compile"("org.jetbrains.kotlinx:kotlinx-io", "kotlinxIoVersion", Platform.COMMON)
    "compile"(project(":modules:core"))
}