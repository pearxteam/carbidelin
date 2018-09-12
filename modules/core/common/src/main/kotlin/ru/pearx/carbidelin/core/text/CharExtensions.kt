/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.core.text

fun charForDigit(digit: Int, radix: Int, uppercase: Boolean = false): Char
{
    return if (digit in 0..(radix - 1) && radix in 2..36)
    {
        if (digit < 10)
            '0' + digit
        else
            (if(uppercase) 'A' else 'a') + digit - 10
    }
    else
    {
        '\u0000'
    }
}