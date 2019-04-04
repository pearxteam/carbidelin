/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.test

import ru.pearx.carbidelin.collections.*
import ru.pearx.carbidelin.collections.event.eventCollectionSimpleBy
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SubListTest {
    //[null, 2, null, 3, 3, null]
    // *yes, I love nulls ❤*
    private fun createCollection() = subListBy(mutableListOf("1", null, "2", null, "3", "3", null, null, "4", "6", "10"), 1, 7)
    private fun createEmptyCollection() = subListBy(mutableListOf<String?>(), 0, 0)

    @Test
    fun testCreation() {
        assertFailsWith<IllegalArgumentException> { subListBy(mutableListOf("1", null, "2"), 2, 1) }
        assertFailsWith<IndexOutOfBoundsException> { subListBy(mutableListOf("1", null, "2"), -2, 1) }
        assertFailsWith<IndexOutOfBoundsException> { subListBy(mutableListOf("1", null, "2"), 0, 4) }
        assertFailsWith<IndexOutOfBoundsException> { subListBy(mutableListOf("1", null, "2"), 3, 4) }
    }

    @Test
    fun testSize() {
        createCollection().apply {
            assertEquals(6, size)
        }
        createEmptyCollection().apply {
            assertEquals(0, size)
        }
    }

    @Test
    fun testIsEmpty() {
        createCollection().apply {
            assertEquals(false, isEmpty())
        }
        createEmptyCollection().apply {
            assertEquals(true, isEmpty())
        }
    }

    @Test
    fun testContains() {
        createCollection().apply {
            assertEquals(true, contains(null))
            assertEquals(true, contains("2"))
            assertEquals(true, contains("3"))
            assertEquals(false, contains("4"))
            assertEquals(false, contains(""))
        }
        createEmptyCollection().apply {
            assertEquals(false, contains(null))
            assertEquals(false, contains("2"))
            assertEquals(false, contains(""))
        }
    }
}