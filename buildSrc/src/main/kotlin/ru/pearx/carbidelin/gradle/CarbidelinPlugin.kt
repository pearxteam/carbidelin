package ru.pearx.carbidelin.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.pearx.carbidelin.gradle.util.CarbidelinProperties
import ru.pearx.carbidelin.gradle.util.Platform

/*
 * Created by mrAppleXZ on 01.09.18.
 */
class CarbidelinPlugin : Plugin<Project>
{
    override fun apply(target: Project)
    {
        val properties = CarbidelinProperties().load(target.rootDir)
        for (module in target.project("modules").subprojects)
        {
            for (platform in module.subprojects)
            {
                Platform.valueOfCodeName(platform.name).applyProject(platform, module, target, properties)
            }
        }
    }
}