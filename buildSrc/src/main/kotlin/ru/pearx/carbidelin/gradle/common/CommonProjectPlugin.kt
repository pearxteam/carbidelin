/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.gradle.common

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.pearx.carbidelin.gradle.util.PROPERTIES_EXTENSION_NAME
import ru.pearx.carbidelin.gradle.util.ProjectProperties

abstract class CommonProjectPlugin : Plugin<Project>
{
    override fun apply(target: Project)
    {
        with(target) {
            val properties = ProjectProperties().load(this)
            extensions.add(PROPERTIES_EXTENSION_NAME, properties)
            apply(this, properties)
        }
    }

    abstract fun apply(target: Project, properties: ProjectProperties)
}