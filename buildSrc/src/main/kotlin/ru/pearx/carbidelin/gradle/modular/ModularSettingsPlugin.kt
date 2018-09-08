/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.gradle.modular

import org.gradle.api.initialization.Settings
import ru.pearx.carbidelin.gradle.common.CommonSettingsPlugin
import ru.pearx.carbidelin.gradle.util.Platform
import ru.pearx.carbidelin.gradle.util.ProjectProperties
import java.io.File
import java.nio.file.Files

/*
 * Created by mrAppleXZ on 31.08.18.
 */
class ModularSettingsPlugin : CommonSettingsPlugin()
{
    override fun apply(target: Settings, properties: ProjectProperties)
    {
        with(target) {
            val root = rootDir.toPath().normalize()
            for(subPath in Files.newDirectoryStream(root.resolve("modules")))
            {
                if(Files.isDirectory(subPath))
                {
                    for(platform in Platform.values())
                    {
                        include(root.relativize(subPath.resolve(platform.codeName)).toString().replace(File.separatorChar, ':'))
                    }
                }
            }
        }
    }
}