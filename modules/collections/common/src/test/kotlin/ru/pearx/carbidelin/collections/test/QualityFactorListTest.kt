/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.test

import ru.pearx.carbidelin.collections.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class QualityFactorListTest {
    @Test
    fun testEmptyCreation() {
        assertEquals(listOf(), qualityFactorListOf<Any>())
        assertEquals(mutableListOf(), mutableQualityFactorListOf<Any>())
    }

    @Test
    fun testNonEmptyCreation() {
        assertEquals(listOf<Pair<Any, Float>>("TEATIME" to 1F, 1024 to 1F, 17091991F to 0.01F), qualityFactorListOf<Any>("TEATIME" to 1F, 1024 to 1F, 17091991F to 0.01F))
        assertEquals(mutableListOf<Pair<Any, Float>>("TEATIME" to 1F, 1024 to 1F, 17091991F to 0.01F), mutableQualityFactorListOf<Any>("TEATIME" to 1F, 1024 to 1F, 17091991F to 0.01F))
    }

    @Test
    fun testParsing() {
        assertEquals(qualityFactorListOf("ru-RU" to 1F, "ru" to 0.9F, "en-US" to 0.8F, "en" to 0.7F), parseQualityFactorList("ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7"))
        assertEquals(qualityFactorListOf("fr-CH" to 1F, "fr" to 0.9F, "en" to 0.8F, "de" to 0.7F, "*" to 0.5F), parseQualityFactorList("fr-CH,fr;q=0.9,en;q=0.8,de;q=0.7,*;q=0.5"))
    }

    @Test
    fun testInvalidParsing() {
        assertFailsWith<IllegalArgumentException> { parseQualityFactorList("test;q=") }
        assertFailsWith<IllegalArgumentException> { parseQualityFactorList("pelmen;q=1;q=2") }
    }

    @Test
    fun testSorting() {
        assertEquals(listOf("ru-RU", "ru", "en-US", "en"), qualityFactorListOf("ru" to 0.9F, "ru-RU" to 1F, "en" to 0.7F, "en-US" to 0.8F).sort())
        assertEquals(listOf("fr-CH", "fr", "en", "de", "*"), qualityFactorListOf("de" to 0.7F, "fr" to 0.9F, "en" to 0.8F, "fr-CH" to 1F, "*" to 0.5F).sort())
    }
}