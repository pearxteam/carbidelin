/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.event

class EventCollection<E>(val base: MutableCollection<E>, private val onUpdate: CollectionEventHandler<E>) : MutableCollection<E> {
    override val size: Int
        get() = base.size

    override fun contains(element: E): Boolean = element in base

    override fun containsAll(elements: Collection<E>): Boolean = base.containsAll(elements)

    override fun isEmpty(): Boolean = base.isEmpty()

    override fun add(element: E): Boolean = base.add(element).ifTrue { onUpdate.onAdd(element) }

    override fun addAll(elements: Collection<E>): Boolean = base.addAll(elements).ifTrue { onUpdate.onAdd(elements) }

    override fun clear() {
        val lst = ArrayList(this)
        base.clear()
        onUpdate.onClear(lst)
    }

    override fun iterator(): MutableIterator<E> = EventMutableIterator(base.iterator(), onUpdate)

    override fun remove(element: E): Boolean = base.remove(element).ifTrue { onUpdate.onRemove(element) }

    override fun removeAll(elements: Collection<E>): Boolean = base.removeAll(elements).ifTrue { onUpdate.onRemove(elements) }

    override fun retainAll(elements: Collection<E>): Boolean {
        var modified = false
        val removed = arrayListOf<E>()
        val iter = base.iterator()
        for (element in iter) {
            if (element !in elements) {
                iter.remove()
                modified = true
                removed.add(element)
            }
        }
        onUpdate.onRemove(elements)
        return modified
    }
}