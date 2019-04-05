/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.event


//region Interfaces
interface IEventCollection<C : MutableCollection<E>, E> : MutableCollection<E> {
    override val size: Int
        get() = base.size

    override fun contains(element: E): Boolean = element in base

    override fun containsAll(elements: Collection<E>): Boolean = base.containsAll(elements)

    override fun isEmpty(): Boolean = base.isEmpty()

    val base: C
}
//endregion


//region Abstracts
abstract class AbstractEventCollectionSimple<C : MutableCollection<E>, E>(override val base: C, protected val onUpdate: CollectionEventHandlerSimple) : IEventCollection<C, E> {
    override fun add(element: E): Boolean = base.add(element).ifTrue(onUpdate)

    override fun addAll(elements: Collection<E>): Boolean = base.addAll(elements).ifTrue(onUpdate)

    override fun clear() {
        val previousSize = base.size
        base.clear()
        if (previousSize != base.size)
            onUpdate()
    }

    override fun iterator(): MutableIterator<E> = EventMutableIteratorSimple(base.iterator(), onUpdate)

    override fun remove(element: E): Boolean = base.remove(element).ifTrue(onUpdate)

    override fun removeAll(elements: Collection<E>): Boolean = base.removeAll(elements).ifTrue(onUpdate)

    override fun retainAll(elements: Collection<E>): Boolean = base.retainAll(elements).ifTrue(onUpdate)

    override fun equals(other: Any?): Boolean = base == other

    override fun hashCode(): Int = base.hashCode()

    override fun toString(): String = base.toString()
}

abstract class AbstractEventCollection<C : MutableCollection<E>, E, U : AbstractCollectionEventHandler<E>>(override val base: C, protected val onUpdate: U) : IEventCollection<C, E> {
    override fun clear() {
        val lst = ArrayList(this)
        base.clear()
        onUpdate.onClear(lst)
    }

    override fun equals(other: Any?): Boolean = base == other

    override fun hashCode(): Int = base.hashCode()

    override fun toString(): String = base.toString()
}
//endregion


//region CollectionSimple
open class EventCollectionSimple<C : MutableCollection<E>, E>(base: C, onUpdate: CollectionEventHandlerSimple) : AbstractEventCollectionSimple<C, E>(base, onUpdate)
open class EventCollectionSimpleRA<C : MutableCollection<E>, E>(base: C, onUpdate: CollectionEventHandlerSimple) : EventCollectionSimple<C, E>(base, onUpdate), RandomAccess
//endregion


//region Collection
open class EventCollection<C : MutableCollection<E>, E>(base: C, onUpdate: CollectionEventHandler<E>) : AbstractEventCollection<C, E, CollectionEventHandler<E>>(base, onUpdate) {
    override fun add(element: E): Boolean = base.add(element).ifTrue { onUpdate.onAdd(element) }

    override fun addAll(elements: Collection<E>): Boolean = base.addAll(elements).ifTrue { onUpdate.onAdd(elements) }

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
open class EventCollectionRA<C : MutableCollection<E>, E>(base: C, onUpdate: CollectionEventHandler<E>) : EventCollection<C, E>(base, onUpdate), RandomAccess
//endregion


//region Factories
fun <C : MutableCollection<E>, E> eventCollectionSimpleBy(base: C, onUpdate: CollectionEventHandlerSimple): IEventCollection<C, E> = if(base is RandomAccess) EventCollectionSimpleRA(base, onUpdate) else EventCollectionSimple(base, onUpdate)
fun <C : MutableCollection<E>, E> eventCollectionBy(base: C, onUpdate: CollectionEventHandler<E>): IEventCollection<C, E> = if(base is RandomAccess) EventCollectionRA(base, onUpdate) else EventCollection(base, onUpdate)
inline fun <C : MutableCollection<E>, E> eventCollectionBy(base: C, crossinline block: CollectionEventHandlerScope<E>.() -> Unit): IEventCollection<C, E> = eventCollectionBy(base, CollectionEventHandlerScope<E>().also(block).createHandler())
//endregion