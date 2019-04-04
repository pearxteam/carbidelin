/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.event

class EventMutableIteratorSimple<T>(private val base: MutableIterator<T>, private val onUpdate: CollectionEventHandlerSimple) : MutableIterator<T> by base {
    override fun remove() {
        base.remove()
        onUpdate()
    }
}

class EventMutableIterator<T>(private val base: MutableIterator<T>, private val onUpdate: CollectionEventHandler<T>) : MutableIterator<T> by base {
    private var lastElement: T? = null

    override fun next(): T = base.next().also { lastElement = it }

    override fun remove() {
        base.remove()
        onUpdate.onRemove(lastElement as T)
    }
}

class EventMutableListIteratorSimple<T>(private val base: MutableListIterator<T>, private val onUpdate: CollectionEventHandlerSimple) : MutableListIterator<T> by base {
    private var lastElement: T? = null

    override fun next(): T = base.next().also { lastElement = it }

    override fun previous(): T = base.previous().also { lastElement = it }

    override fun add(element: T) {
        base.add(element)
        onUpdate()
    }

    override fun remove() {
        base.remove()
        onUpdate()
    }

    override fun set(element: T) {
        base.set(element)
        if(lastElement != element)
            onUpdate()
    }
}