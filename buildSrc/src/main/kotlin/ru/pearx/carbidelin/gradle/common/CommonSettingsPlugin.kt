/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.gradle.common

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import ru.pearx.carbidelin.gradle.util.ProjectProperties


/*
 * Created by mrAppleXZ on 06.09.18.
 */
abstract class CommonSettingsPlugin : Plugin<Settings>
{
    override fun apply(target: Settings)
    {
        with(target) {
            val properties = ProjectProperties().load(this)
            rootProject.name = properties.projectName
            apply(this, properties)
        }
    }

    abstract fun apply(target: Settings, properties: ProjectProperties)
}