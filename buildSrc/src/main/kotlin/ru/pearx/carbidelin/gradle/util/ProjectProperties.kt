/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.gradle.util

import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


/*
 * Created by mrAppleXZ on 01.09.18.
 */
class ProjectProperties
{
    private val props = Properties()

    lateinit var projectName: String private set
    lateinit var kotlinVersion: String private set
    lateinit var nodejsPluginVersion: String private set
    lateinit var nodejsVersion: String private set
    lateinit var npmVersion: String private set
    lateinit var mochaVersion: String private set
    lateinit var junitJupiterVersion: String private set
    lateinit var jacocoVersion: String private set

    fun load(project: Project) = load(project.rootDir)

    fun load(settings: Settings) = load(settings.rootDir)

    fun load(propertiesDir: File): ProjectProperties
    {
        val classProps = this::class.memberProperties
        val stringType = String::class.createType()
        FileReader(File(propertiesDir, "project.properties")).use {
            props.load(it)
        }
        for ((key, value) in props)
        {
            for (property in classProps)
            {
                if (property.name == key && property is KMutableProperty<*> && property.returnType == stringType)
                {
                    property.isAccessible = true
                    @Suppress("UNCHECKED_CAST")
                    (property as KMutableProperty<String>).setter.call(this, value)
                }
            }
        }
        return this
    }

    operator fun get(name: String) = props.get(name) as String
}