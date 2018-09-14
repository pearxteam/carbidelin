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

fun decodeUriComponent(s: String, spaceMode: SpaceEncodingMode = SpaceEncodingMode.PLUS): String
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
                // check that the string doesn't end at the first or second character of percent sequence
                if (s.length > index + 2)
                {
                    val hex0 = s[++index]
                    val hex1 = s[++index]

                    // check that the $hex0 and $hex1 are both valid hexadecimal characters [0-9A-Za-z]
                    if (hex0.isValidForRadix(16) && hex1.isValidForRadix(16))
                        buf[bufWritten++] = ((hex0.toDigit() shl 4) or (hex1.toDigit())).toByte()
                    else
                        throw UriDecoderException("There's an invalid character in the percent-encoding sequence '%$hex0$hex1'")
                }
                else
                    throw UriDecoderException("Incomplete percent-encoding sequence '%${if (s.length > index + 1) s[index + 1] else ""}${if (s.length > index + 2) s[index + 2] else ""}'")
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