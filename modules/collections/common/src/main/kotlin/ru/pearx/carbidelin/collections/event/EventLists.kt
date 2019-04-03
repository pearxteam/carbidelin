/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections.event

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