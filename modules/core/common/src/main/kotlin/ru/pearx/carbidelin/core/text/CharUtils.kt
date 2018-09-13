/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.core.text

import kotlin.math.min

fun Char.isValidForRadix(radix: Int): Boolean
{
    return when
    {
        radix > 0 && this in '0'..('0' - 1 + min(radix, 10)) -> true
        radix > 10 && this.toUpperCase() in 'A'..('A' - 1 + min(radix - 10, 26)) -> true
        else -> false
    }
}

fun Char.toDigit(): Int
{
    return when(this)
    {
        in '0'..'9' -> this - '0'
        in 'A'..'Z' -> 10 + (this - 'A')
        in 'a'..'z' -> 10 + (this - 'a')
        else -> throw IllegalArgumentException("The character '$this' must be in one of these ranges: [0, 9], [a, z], [A, Z]")
    }
}

fun charForDigit(digit: Int, radix: Int, uppercase: Boolean = false): Char
{
    return if (digit in 0..(radix - 1) && radix in 2..36)
    {
        if (digit < 10)
            '0' + digit
        else
            (if (uppercase) 'A' else 'a') + digit - 10
    }
    else
    {
        '\u0000'
    }
}