/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.collections

typealias QualityFactorList<T> = List<Pair<T, Float>>
typealias MutableQualityFactorList<T> = MutableList<Pair<T, Float>>

fun <T> QualityFactorList<T>.sort(): List<T> = sortedByDescending { it.second }.map { it.first }

fun <T> qualityFactorListOf() = listOf<Pair<T, Float>>()
fun <T> qualityFactorListOf(vararg elements: Pair<T, Float>) = listOf(*elements)
fun <T> mutableQualityFactorListOf() = mutableListOf<Pair<T, Float>>()
fun <T> mutableQualityFactorListOf(vararg elements: Pair<T, Float>) = mutableListOf(*elements)

private fun getExceptionMessage(input: String) = "Invalid quality factor-formatted string: '$input'."

fun parseQualityFactorList(input: String): QualityFactorList<String> {
    //fr-CH,fr;q=0.9,en;q=0.8,de;q=0.7,*;q=0.5
    val output = mutableQualityFactorListOf<String>()
    if(input.isEmpty())
        return output
    for (loc in input.split(',')) {
        val locSplit = loc.split(";q=")
        if (locSplit.size in 1..2) {
            val name = locSplit[0]
            val quality = if (locSplit.size == 2) {
                try {
                    locSplit[1].toFloat()
                }
                catch (e: NumberFormatException) {
                    throw IllegalArgumentException(getExceptionMessage(input), e)
                }
            }
            else 1f

            output.add(Pair(name, quality))
        }
        else {
            throw IllegalArgumentException(getExceptionMessage(input))
        }
    }
    return output
}