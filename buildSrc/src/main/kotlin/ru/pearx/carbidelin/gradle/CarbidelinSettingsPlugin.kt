package ru.pearx.carbidelin.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import ru.pearx.carbidelin.gradle.util.Platform
import java.io.File
import java.nio.file.Files

/*
 * Created by mrAppleXZ on 31.08.18.
 */
class CarbidelinSettingsPlugin : Plugin<Settings>
{
    override fun apply(target: Settings)
    {
        with(target) {
            rootProject.name = "carbidelin"
            val root = rootDir.toPath().normalize();
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