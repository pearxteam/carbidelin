/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.uri.test

import ru.pearx.carbidelin.uri.SpaceEncodingMode
import ru.pearx.carbidelin.uri.encodeUriComponent
import ru.pearx.carbidelin.uri.isNeededToUriEncode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UriEncoderTest
{
    @Test
    fun testNotNeededToEncodeAlphanumeric()
    {
        for (char in 'a'..'z')
            assertFalse { isNeededToUriEncode(char) }
        for (char in 'A'..'Z')
            assertFalse { isNeededToUriEncode(char) }
        for (char in '0'..'9')
            assertFalse { isNeededToUriEncode(char) }
    }

    @Test
    fun testNotNeededToEncodeSpecials()
    {
        assertFalse { isNeededToUriEncode('-') }
        assertFalse { isNeededToUriEncode('_') }
        assertFalse { isNeededToUriEncode('.') }
        assertFalse { isNeededToUriEncode('~') }
    }

    @Test
    fun testNeededToEncode()
    {
        assertTrue { isNeededToUriEncode('Ы') }
        assertTrue { isNeededToUriEncode('/') }
        assertTrue { isNeededToUriEncode('#') }
    }

    @Test
    fun testComponentEncoding()
    {
        assertEquals("%D1%82%D0%BE%D0%BC%D0%B0%D1%82", encodeUriComponent("томат"))
        assertEquals("%F0%9F%91%A8%E2%80%8D", encodeUriComponent("\uD83D\uDC68\u200D"))
        assertEquals("%D0%A8%D0%BA%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA%20%D1%83%D1%82%D0%BE%D0%BD%D1%83%D0%BB%20%D0%B2%20%D0%BB%D1%83%D0%B6%D0%B5%20%D0%B8%20%D1%81%D0%BA%D0%B0%D0%B7%D0%B0%D0%BB%3A%20%22Ouch%21%22.", encodeUriComponent("Школьник утонул в луже и сказал: \"Ouch!\"."))
    }

    @Test
    fun testComponentEncodingWithSpacePlus()
    {
        assertEquals("One%2C+two%2C+three...", encodeUriComponent("One, two, three...", SpaceEncodingMode.PLUS))
    }
}