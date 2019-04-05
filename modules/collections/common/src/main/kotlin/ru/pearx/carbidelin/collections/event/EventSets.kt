/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("NOTHING_TO_INLINE")

package ru.pearx.carbidelin.collections.event

//region Interfaces
interface IEventSet<C : MutableSet<E>, E> : IEventCollection<C, E>, MutableSet<E> {
    override val size: Int
        get() = super.size

    override fun contains(element: E): Boolean = super.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean = super.containsAll(elements)

    override fun isEmpty(): Boolean = super.isEmpty()
}
//endregion


//region SetSimple
open class EventSetSimple<C : MutableSet<E>, E>(base: C, onUpdate: CollectionEventHandlerSimple) : EventCollectionSimple<C, E>(base, onUpdate), IEventSet<C, E>
open class EventSetSimpleRA<C : MutableSet<E>, E>(base: C, onUpdate: CollectionEventHandlerSimple) : EventSetSimple<C, E>(base, onUpdate), RandomAccess
//endregion


//region Set
open class EventSet<C : MutableSet<E>, E>(base: C, onUpdate: CollectionEventHandler<E>) : EventCollection<C, E>(base, onUpdate), IEventSet<C, E>
open class EventSetRA<C : MutableSet<E>, E>(base: C, onUpdate: CollectionEventHandler<E>) : EventSet<C, E>(base, onUpdate), RandomAccess
//endregion


//region Parent Factories
fun <C : MutableSet<E>, E> eventSetSimpleBy(base: C, onUpdate: CollectionEventHandlerSimple): IEventSet<C, E> = if(base is RandomAccess) EventSetSimpleRA(base, onUpdate) else EventSetSimple(base, onUpdate)
fun <C : MutableSet<E>, E> eventSetBy(base: C, onUpdate: CollectionEventHandler<E>): IEventSet<C, E> = if(base is RandomAccess) EventSetRA(base, onUpdate) else EventSet(base, onUpdate)
inline fun <C : MutableSet<E>, E> eventSetBy(base: C, crossinline block: CollectionEventHandlerScope<E>.() -> Unit): IEventSet<C, E> = eventSetBy(base, CollectionEventHandlerScope<E>().also(block).createHandler())
//endregion


//region Child Factories
fun <E> eventSetSimpleOf(vararg elements: E, onUpdate: CollectionEventHandlerSimple): IEventSet<MutableSet<E>, E> = eventSetSimpleBy(mutableSetOf(*elements), onUpdate)
inline fun <E> eventSetOf(vararg elements: E, onUpdate: CollectionEventHandler<E>): IEventSet<MutableSet<E>, E> = eventSetBy(mutableSetOf(*elements), onUpdate)
inline fun <E> eventSetOf(vararg elements: E, crossinline block: CollectionEventHandlerScope<E>.() -> Unit): IEventSet<MutableSet<E>, E> = eventSetBy(mutableSetOf(*elements), block)

fun <E> eventHashSetSimpleOf(vararg elements: E, onUpdate: CollectionEventHandlerSimple): IEventSet<MutableSet<E>, E> = eventSetSimpleBy(hashSetOf(*elements), onUpdate)
inline fun <E> eventHashSetOf(vararg elements: E, onUpdate: CollectionEventHandler<E>): IEventSet<MutableSet<E>, E> = eventSetBy(hashSetOf(*elements), onUpdate)
inline fun <E> eventHashSetOf(vararg elements: E, crossinline block: CollectionEventHandlerScope<E>.() -> Unit): IEventSet<MutableSet<E>, E> = eventSetBy(hashSetOf(*elements), block)

fun <E> eventLinkedSetSimpleOf(vararg elements: E, onUpdate: CollectionEventHandlerSimple): IEventSet<MutableSet<E>, E> = eventSetSimpleBy(linkedSetOf(*elements), onUpdate)
inline fun <E> eventLinkedSetOf(vararg elements: E, onUpdate: CollectionEventHandler<E>): IEventSet<MutableSet<E>, E> = eventSetBy(linkedSetOf(*elements), onUpdate)
inline fun <E> eventLinkedSetOf(vararg elements: E, crossinline block: CollectionEventHandlerScope<E>.() -> Unit): IEventSet<MutableSet<E>, E> = eventSetBy(linkedSetOf(*elements), block)
//endregion