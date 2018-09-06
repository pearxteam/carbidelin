package ru.pearx.carbidelin.gradle.multiplatform

import org.gradle.api.initialization.Settings
import ru.pearx.carbidelin.gradle.common.CommonSettingsPlugin
import ru.pearx.carbidelin.gradle.util.Platform
import ru.pearx.carbidelin.gradle.util.ProjectProperties


/*
 * Created by mrAppleXZ on 06.09.18.
 */
class MultiplatformSettingsPlugin : CommonSettingsPlugin()
{
    override fun apply(target: Settings, properties: ProjectProperties)
    {
        with(target) {
            for (platform in Platform.values())
                include(platform.codeName)
        }
    }
}