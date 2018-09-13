/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.uri

import kotlinx.io.core.String
import ru.pearx.carbidelin.core.text.isValidForRadix
import ru.pearx.carbidelin.core.text.toDigit

fun decodeUriComponent(s: String, spaceMode: SpaceEncodingMode = SpaceEncodingMode.PERCENT_ENCODED): String
{
    return StringBuilder(s.length).apply {
        val buf = ByteArray(s.length / 3)
        var bufWritten = 0

        var index = 0
        while (index < s.length)
        {
            val char = s[index]

            if (char == '%')
            {
                //todo throw exceptions
                val hex0 = s[++index]
                val hex1 = s[++index]
                if(hex0.isValidForRadix(16) && hex1.isValidForRadix(16))
                {
                    buf[bufWritten++] = ((hex0.toDigit() shl 4) or (hex1.toDigit())).toByte()
                }
            }
            else
            {
                if (bufWritten > 0)
                {
                    append(String(buf, length = bufWritten)) // flush the buffer
                    bufWritten = 0
                }

                if (char == '+' && spaceMode == SpaceEncodingMode.PLUS)
                    append(' ')
                else
                    append(char)
            }
            index++
        }

        if (bufWritten > 0)
            append(String(buf, length = bufWritten)) // flush the buffer
    }.toString()
}