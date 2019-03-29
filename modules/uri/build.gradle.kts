/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import ru.pearx.multigradle.util.*
import ru.pearx.multigradle.util.Platform
import ru.pearx.multigradle.util.js.MultiGradleJsExtension

subplatforms {
    dependencies {
        "api"(mpdep("org.jetbrains.kotlinx:kotlinx-io:${propertyString("kotlinxIoVersion")}", PLATFORM_COMMON))
        "api"(mpdep(project(":modules:core")))
    }
    ifPlatform(PLATFORM_JS) {
        configure<MultiGradleJsExtension> {
            npmPackages {
                put("text-encoding", propertyString("textEncodingVersion"))
            }
        }
        dependencies {
            "implementation"(kotlin("test-js")) // hack: kotlinx-io-js depends on the kotlin-test-js :/
        }
    }
}