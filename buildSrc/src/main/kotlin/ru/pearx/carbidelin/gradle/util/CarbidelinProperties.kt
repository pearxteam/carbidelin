package ru.pearx.carbidelin.gradle.util

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
class CarbidelinProperties
{
    lateinit var kotlinVersion: String private set
    lateinit var nodejsPluginVersion: String private set
    lateinit var nodejsVersion: String private set
    lateinit var npmVersion: String private set
    lateinit var mochaVersion: String private set
    lateinit var junitJupiterVersion: String private set
    lateinit var jacocoVersion: String private set

    fun load(propertiesDir: File): CarbidelinProperties
    {
        val props = Properties()
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
                    (property as KMutableProperty<String>).setter.call(this, value)
                }
            }
        }
        return this
    }
}