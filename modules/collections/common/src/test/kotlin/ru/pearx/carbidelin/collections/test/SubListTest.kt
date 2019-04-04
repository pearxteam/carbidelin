/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.test

import ru.pearx.carbidelin.collections.subListBy
import kotlin.test.*

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
            assertFalse(isEmpty())
        }
        createEmptyCollection().apply {
            assertTrue(isEmpty())
        }
    }

    @Test
    fun testContains() {
        createCollection().apply {
            assertTrue(contains(null))
            assertTrue(contains("2"))
            assertTrue(contains("3"))
            assertFalse(contains("4"))
            assertFalse(contains(""))
        }
        createEmptyCollection().apply {
            assertFalse(contains(null))
            assertFalse(contains("2"))
            assertFalse(contains(""))
        }
    }

    @Test
    fun testContainsAll() {
        createCollection().apply {
            assertTrue(containsAll(listOf()))
            assertTrue(containsAll(listOf(null)))
            assertTrue(containsAll(listOf("2")))
            assertTrue(containsAll(listOf(null, "3", "2")))
            assertFalse(containsAll(listOf("6", "10")))
            assertFalse(containsAll(listOf(null, "3", "2", "10")))
        }
        createEmptyCollection().apply {
            assertTrue(containsAll(listOf()))
            assertFalse(containsAll(listOf("free time")))
            assertFalse(containsAll(listOf(null, "3", "2", "pineapple")))
        }
    }

    @Test
    fun testIterator() {
        createCollection().also { oldList ->
            assertEquals(listOf(null, "2", null, "3", "3", null), mutableListOf<String?>().also { newList ->
                for (el in oldList) {
                    newList.add(el)
                }
            })
        }

        createCollection().apply {
            val iter = iterator()
            var counter = 0
            for (el in iter) {
                if(counter % 2 == 0)
                    iter.remove()
                counter++
            }
            assertEquals(listOf("2", "3", null), this)
        }
    }

    //todo
}