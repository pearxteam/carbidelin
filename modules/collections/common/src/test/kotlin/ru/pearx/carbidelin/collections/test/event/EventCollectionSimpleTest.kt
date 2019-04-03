/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.test.event

import ru.pearx.carbidelin.collections.event.EventCollectionSimple
import ru.pearx.carbidelin.collections.event.eventCollectionSimpleBy
import kotlin.test.Test
import kotlin.test.assertEquals

class EventCollectionSimpleTest {
    inner class TestingContext(empty: Boolean = false) {
        var modified = false
        var collection = eventCollectionSimpleBy(if(empty) mutableSetOf() else mutableSetOf("theevilroot", "root", null, "")) { modified = true }
    }

    @Test
    fun testNotChangedMethods() {
        TestingContext().collection.apply {
            assertEquals(4, size)

            assertEquals(true, contains(null))
            assertEquals(true, contains("root"))
            assertEquals(false, contains("doesnt-exist"))

            assertEquals(false, containsAll(listOf(null, "STRING")))
            assertEquals(false, containsAll(listOf("doesnt-exist-too", "doesnt-exist")))
            assertEquals(true, containsAll(listOf("root", "theevilroot")))

            assertEquals(false, isEmpty())
        }

        TestingContext(true).collection.apply {
            assertEquals(0, size)
            assertEquals(true, isEmpty())
        }
    }

    @Test
    fun testAdd() {
        TestingContext().apply {
            collection.add("theevilroot")
            assertEquals(false, modified)
            assertEquals(setOf("theevilroot", "root", null, ""), collection.base)
        }

        TestingContext().apply {
            collection.add("theevilroot1")
            assertEquals(true, modified)
            assertEquals(setOf("theevilroot", "root", null, "", "theevilroot1"), collection.base)
        }
    }

    @Test
    fun testAddAll() {
        TestingContext().apply {
            collection.addAll(listOf(null, "root"))
            assertEquals(false, modified)
            assertEquals(setOf("theevilroot", "root", null, ""), collection.base)
        }

        TestingContext().apply {
            collection.addAll(listOf("theevilroot", "theevilroot1"))
            assertEquals(true, modified)
            assertEquals(setOf("theevilroot", "root", null, "", "theevilroot1"), collection.base)
        }

        TestingContext().apply {
            collection.addAll(listOf("openwrt", "ddwrt"))
            assertEquals(true, modified)
            assertEquals(setOf("theevilroot", "root", null, "", "openwrt", "ddwrt"), collection.base)
        }
    }

    @Test
    fun testClear() {
        TestingContext(true).apply {
            collection.clear()
            assertEquals(false, modified)
            assertEquals(setOf<String?>(), collection.base)
        }

        TestingContext().apply {
            collection.clear()
            assertEquals(true, modified)
            assertEquals(setOf<String?>(), collection.base)
        }
    }

    @Test
    fun testIterator() {
        TestingContext().apply {
            val iter = collection.iterator()
            var num = 0
            for(element in iter) {
                num++
            }
            assertEquals(4, num)
            assertEquals(false, modified)
            assertEquals(setOf("theevilroot", "root", null, ""), collection.base)
        }

        TestingContext().apply {
            val iter = collection.iterator()
            var num = 0
            for(element in iter) {
                if(element == null)
                    iter.remove()
                num++
            }
            assertEquals(4, num)
            assertEquals(true, modified)
            assertEquals(setOf<String?>("theevilroot", "root", ""), collection.base)
        }
    }

    @Test
    fun testRemove() {
        TestingContext().apply {
            collection.remove("theevilroot1")
            assertEquals(false, modified)
            assertEquals(setOf("theevilroot", "root", null, ""), collection.base)
        }

        TestingContext().apply {
            collection.remove("theevilroot")
            assertEquals(true, modified)
            assertEquals(setOf("root", null, ""), collection.base)
        }
    }

    @Test
    fun testRemoveAll() {
        TestingContext().apply {
            collection.removeAll(listOf("openwrt", "ddwrt"))
            assertEquals(false, modified)
            assertEquals(setOf("theevilroot", "root", null, ""), collection.base)
        }

        TestingContext().apply {
            collection.removeAll(listOf(null, "root"))
            assertEquals(true, modified)
            assertEquals(setOf<String?>("theevilroot", ""), collection.base)
        }

        TestingContext().apply {
            collection.removeAll(listOf("theevilroot", "theevilroot1"))
            assertEquals(true, modified)
            assertEquals(setOf("root", null, ""), collection.base)
        }
    }

    @Test
    fun testRetainAll() {
        TestingContext().apply {
            collection.retainAll(listOf("theevilroot", "root", null, ""))
            assertEquals(false, modified)
            assertEquals(setOf("theevilroot", "root", null, ""), collection.base)
        }

        TestingContext().apply {
            collection.retainAll(listOf(null, "", "theevilroot1"))
            assertEquals(true, modified)
            assertEquals(setOf(null, ""), collection.base)
        }

        TestingContext().apply {
            collection.retainAll(listOf("theevilroot1"))
            assertEquals(true, modified)
            assertEquals(setOf<String?>(), collection.base)
        }
    }
}