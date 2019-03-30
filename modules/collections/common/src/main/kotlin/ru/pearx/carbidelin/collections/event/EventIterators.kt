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

class EventMutableIterator<T>(private val base: MutableIterator<T>, private val onUpdate: CollectionEventHandler<T>) : MutableIterator<T> {
    private var lastElement: T? = null

    override fun hasNext(): Boolean = base.hasNext()

    override fun next(): T = base.next().also { lastElement = it }

    override fun remove() {
        base.remove()
        onUpdate.onRemove(lastElement as T)
    }
}