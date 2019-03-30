/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.event

class EventCollectionSimple<E>(val base: MutableCollection<E>, private val onUpdate: CollectionEventHandlerSimple) : MutableCollection<E> {
    override val size: Int
        get() = base.size

    override fun contains(element: E): Boolean = element in base

    override fun containsAll(elements: Collection<E>): Boolean = base.containsAll(elements)

    override fun isEmpty(): Boolean = base.isEmpty()

    override fun add(element: E): Boolean = base.add(element).ifTrue { onUpdate() }

    override fun addAll(elements: Collection<E>): Boolean = base.addAll(elements).ifTrue { onUpdate() }

    override fun clear() {
        val previousSize = base.size
        base.clear()
        if (previousSize != base.size)
            onUpdate()
    }

    override fun iterator(): MutableIterator<E> = EventMutableIteratorSimple(base.iterator(), onUpdate)

    override fun remove(element: E): Boolean = base.remove(element).ifTrue { onUpdate() }

    override fun removeAll(elements: Collection<E>): Boolean = base.removeAll(elements).ifTrue { onUpdate() }

    override fun retainAll(elements: Collection<E>): Boolean = base.retainAll(elements).ifTrue { onUpdate() }

    override fun toString(): String = base.toString()

    override fun equals(other: Any?): Boolean = base == other

    override fun hashCode(): Int = base.hashCode()
}