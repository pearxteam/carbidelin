/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.uri

import kotlinx.io.charsets.Charsets
import kotlinx.io.charsets.encodeToByteArray
import ru.pearx.carbidelin.core.text.charForDigit

fun isNeededToUriEncode(char: Char): Boolean
{
    return when(char)
    {
        in 'a'..'z', in 'A'..'Z', in '0'..'9', '-', '_', '.', '~' -> false
        else -> true
    }
}

fun encodeUriComponent(s: String, spaceMode: SpaceEncodingMode = SpaceEncodingMode.PERCENT_ENCODED): String
{
    return StringBuilder(s.length).apply {
        val encoder = Charsets.UTF_8.newEncoder()
        val buf = StringBuilder()
        for(char in s)
        {
            when
            {
                !isNeededToUriEncode(char) -> append(char)
                char == ' ' && spaceMode == SpaceEncodingMode.PLUS -> append('+')
                else ->
                {
                    buf.append(char)
                    if(!char.isSurrogate())
                    {
                        val arr = encoder.encodeToByteArray(buf)
                        for (byte in arr)
                        {
                            append('%')
                            append(charForDigit((byte.toInt() ushr 4) and 0xF, 16, true))
                            append(charForDigit(byte.toInt() and 0xF, 16, true))
                        }
                        buf.clear()
                    }
                }
            }
        }
    }.toString()
}