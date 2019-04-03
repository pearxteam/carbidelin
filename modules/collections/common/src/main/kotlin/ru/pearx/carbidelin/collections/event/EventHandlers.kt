/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.event

typealias CollectionEventHandlerSimple = () -> Unit

typealias ListEventHandlerSimple = () -> Unit

interface CollectionEventHandler<T> {
    fun onAdd(element: T)
    fun onAdd(elements: Collection<T>)
    fun onRemove(element: T)
    fun onRemove(elements: Collection<T>)
    fun onClear(elements: Collection<T>)
}

interface ListEventHandler<T> {
    fun onAdd(element: T)
    fun onAdd(elements: Collection<T>)
    fun onRemove(element: T)
    fun onRemove(elements: Collection<T>)
    fun onClear(elements: Collection<T>)
}

// BUILDER
private typealias ElementBlock<T> = (element: T) -> Unit
private typealias ElementBlocks<T> = (elements: Collection<T>) -> Unit

class CollectionEventHandlerScope<T> {
    private lateinit var addBlock: ElementBlock<T>
    private lateinit var addAllBlock: ElementBlocks<T>
    private lateinit var removeBlock: ElementBlock<T>
    private lateinit var removeAllBlock: ElementBlocks<T>
    private lateinit var clearBlock: ElementBlocks<T>

    fun add(block: ElementBlock<T>) {
        addBlock = block
    }

    fun addAll(block: ElementBlocks<T>) {
        addAllBlock = block
    }

    fun remove(block: ElementBlock<T>) {
        removeBlock = block
    }

    fun removeAll(block: ElementBlocks<T>) {
        removeAllBlock = block
    }

    fun clear(block: ElementBlocks<T>) {
        clearBlock = block
    }

    @PublishedApi
    internal fun createHandler(): CollectionEventHandler<T> = object : CollectionEventHandler<T> {
        override fun onAdd(element: T) {
            addBlock(element)
        }

        override fun onAdd(elements: Collection<T>) {
            addAllBlock(elements)
        }

        override fun onRemove(element: T) {
            removeBlock(element)
        }

        override fun onRemove(elements: Collection<T>) {
            removeAllBlock(elements)
        }

        override fun onClear(elements: Collection<T>) {
            clearBlock(elements)
        }
    }
}