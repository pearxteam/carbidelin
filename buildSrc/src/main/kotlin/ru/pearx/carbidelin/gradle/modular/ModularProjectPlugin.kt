package ru.pearx.carbidelin.gradle.modular

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.pearx.carbidelin.gradle.util.Platform
import ru.pearx.carbidelin.gradle.util.ProjectProperties

/*
 * Created by mrAppleXZ on 01.09.18.
 */
class ModularProjectPlugin : Plugin<Project>
{
    override fun apply(target: Project)
    {
        with(target) {
            val properties = ProjectProperties().load(this)
            for (module in project("modules").subprojects)
            {
                for (platform in module.subprojects)
                {
                    Platform.valueOfCodeName(platform.name).applyProject(platform, module, this, properties)
                }
            }
        }
    }
}