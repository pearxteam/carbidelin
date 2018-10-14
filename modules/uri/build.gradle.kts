import ru.pearx.multigradle.util.*
import ru.pearx.multigradle.util.Platform
import ru.pearx.multigradle.util.js.MultiGradleJsExtension

subplatforms {
    dependencies {
        "compile"(mpdep("org.jetbrains.kotlinx:kotlinx-io:${propertyString("kotlinxIoVersion")}", PLATFORM_COMMON))
        "compile"(mpdep(project(":modules:core")))
    }
    ifPlatform(PLATFORM_JS) {
        configure<MultiGradleJsExtension> {
            npmPackages {
                put("text-encoding", propertyString("textEncodingVersion"))
            }
        }
        dependencies {
            "compile"(kotlin("test-js"))
        }
    }
}