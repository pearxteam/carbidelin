/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.gradle.modular

import org.gradle.api.Project
import ru.pearx.carbidelin.gradle.util.Platform


/*
 * Created by mrAppleXZ on 06.09.18.
 */
fun Project.multiplatformDependencies(init: MultiplatformDependenciesScope.() -> Unit) =
        MultiplatformDependenciesScope(this).apply { init() }

class MultiplatformDependenciesScope(private val project: Project)
{
    operator fun String.invoke(projectDependency: Project)
    {
        with(project) {
            for (platform in Platform.values())
            {
                project(platform.codeName).dependencies.add(this@invoke, projectDependency.project(platform.codeName))
            }
        }
    }
}