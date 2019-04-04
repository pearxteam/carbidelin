/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.event

import ru.pearx.carbidelin.collections.subListBy

interface IEventList<C : MutableList<E>, E> : IEventCollection<C, E>, MutableList<E> {
    override val size: Int
        get() = super.size

    override fun containsAll(elements: Collection<E>): Boolean = super.containsAll(elements)

    override fun contains(element: E): Boolean = super.contains(element)

    override fun isEmpty(): Boolean = super.isEmpty()

    override fun get(index: Int): E = base[index]

    override fun indexOf(element: E): Int = base.indexOf(element)

    override fun lastIndexOf(element: E): Int = base.lastIndexOf(element)
}

open class EventListSimple<C : MutableList<E>, E>(base: C, onUpdate: ListEventHandlerSimple) : EventCollectionSimple<C, E>(base, onUpdate), IEventList<C, E> {
    override fun add(index: Int, element: E) = base.add(index, element).also { onUpdate() }

    override fun addAll(index: Int, elements: Collection<E>): Boolean = base.addAll(index, elements).ifTrue(onUpdate)

    override fun listIterator(): MutableListIterator<E> = EventMutableListIteratorSimple(base.listIterator(), onUpdate)

    override fun listIterator(index: Int): MutableListIterator<E> = EventMutableListIteratorSimple(base.listIterator(index), onUpdate)

    override fun removeAt(index: Int): E = base.removeAt(index).also { onUpdate() }

    override fun set(index: Int, element: E): E = base.set(index, element).also { if(element != it) onUpdate() }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> = subListBy(this, fromIndex, toIndex)
}