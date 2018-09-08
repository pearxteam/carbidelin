/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.gradle.multiplatform

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.pearx.carbidelin.gradle.util.Platform
import ru.pearx.carbidelin.gradle.util.ProjectProperties


/*
 * Created by mrAppleXZ on 06.09.18.
 */
class MultiplatformProjectPlugin : Plugin<Project>
{
    override fun apply(target: Project)
    {
        with(target) {
            val properties = ProjectProperties().load(this)
            for (platform in subprojects)
            {
                Platform.valueOfCodeName(platform.name).applyProject(platform, this, this, properties)
            }
        }
    }
}