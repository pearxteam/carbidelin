/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections

open class SubList<E>(private val base: MutableList<E>, fromIndex: Int, toIndex: Int) : AbstractMutableList<E>() {
    private var _size = toIndex - fromIndex
    private val offset = fromIndex

    init {
        when {
            fromIndex < 0 -> throw IndexOutOfBoundsException("fromIndex($fromIndex) < 0")
            toIndex > base.size -> throw IndexOutOfBoundsException("toIndex($toIndex) > size(${base.size})")
            fromIndex > toIndex -> throw IllegalArgumentException("fromIndex($fromIndex) > toIndex($toIndex)")
        }
    }

    override val size: Int
        get() = _size

    override fun add(element: E): Boolean = add(size, element).let { true }

    override fun add(index: Int, element: E) = checkIndexAndThrow(index).let { base.add(index, element) }.also { _size++ }

    override fun addAll(index: Int, elements: Collection<E>): Boolean = checkIndexAndThrow(index).let { base.addAll(index, elements) }.also { _size += elements.size }

    override fun addAll(elements: Collection<E>): Boolean = addAll(size, elements)

    override fun clear() {
        val it = iterator()
        for (el in it)
            it.remove()
    }

    override fun contains(element: E): Boolean = indexOf(element) != -1

    override fun containsAll(elements: Collection<E>): Boolean = elements.none { it !in this }

    override fun indexOf(element: E): Int {
        val it = listIterator()
        while (it.hasNext())
            if (element == it.next())
                return it.previousIndex()
        return -1
    }

    override fun get(index: Int): E = checkIndexAndThrow(index).let { base[index + offset] }

    override fun iterator(): MutableIterator<E> = listIterator()

    override fun lastIndexOf(element: E): Int {
        val it = listIterator(size)
        while (it.hasPrevious())
            if (element == it.previous())
                return it.nextIndex()
        return -1
    }

    override fun listIterator(): MutableListIterator<E> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<E> = checkIndexAndThrow(index).let {
        object : MutableListIterator<E> {
            val baseIterator = base.listIterator(index)

            override fun hasPrevious(): Boolean = checkIndex(previousIndex()) && baseIterator.hasPrevious()

            override fun nextIndex(): Int = baseIterator.nextIndex() - offset

            override fun previous(): E = if(hasPrevious()) baseIterator.previous() else throw NoSuchElementException()

            override fun previousIndex(): Int = baseIterator.previousIndex() - offset

            override fun add(element: E) = baseIterator.add(element).also { _size++ }

            override fun hasNext(): Boolean = checkIndex(nextIndex()) && baseIterator.hasNext()

            override fun next(): E = if(hasNext()) baseIterator.next() else throw NoSuchElementException()

            override fun remove() = baseIterator.remove().also { _size-- }

            override fun set(element: E) = baseIterator.set(element)
        }
    }

    override fun remove(element: E): Boolean {
        val index = indexOf(element)
        if (index != -1) {
            removeAt(index)
            return true
        }
        return false
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        var flag = false
        for (element in elements) {
            if (remove(element))
                flag = true
        }
        return flag
    }

    override fun removeAt(index: Int): E = checkIndexAndThrow(index).let { base.removeAt(index) }.also { _size-- }

    override fun retainAll(elements: Collection<E>): Boolean {
        var flag = false
        for (element in elements) {
            val index = indexOf(element)
            if (index != -1) {
                removeAt(index)
                flag = true
            }
        }
        return flag
    }

    override fun set(index: Int, element: E): E = checkIndexAndThrow(index).let { base.set(index + offset, element) }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> = subListBy(this, fromIndex, toIndex)

    private fun checkIndex(index: Int) = index in 0..(size - 1)

    private fun checkIndexAndThrow(index: Int) {
        if (!checkIndex(index))
            throwOutOfBounds(index)
    }

    private fun throwOutOfBounds(index: Int): String {
        throw IndexOutOfBoundsException("index: $index, size: $size")
    }
}

open class SubListRA<E>(base: MutableList<E>, fromIndex: Int, toIndex: Int) : SubList<E>(base, fromIndex, toIndex), RandomAccess

fun <E> subListBy(base: MutableList<E>, fromIndex: Int, toIndex: Int) = if (base is RandomAccess) SubListRA(base, fromIndex, toIndex) else SubList(base, fromIndex, toIndex)