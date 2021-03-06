/*
 * Copyright © 2019, PearX Team
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.uri.test

import ru.pearx.carbidelin.uri.SpaceEncodingMode
import ru.pearx.carbidelin.uri.UriDecoderException
import ru.pearx.carbidelin.uri.decodeUriComponent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UriDecoderTest {
    @Test
    fun testComponent() {
        assertEquals("томат", decodeUriComponent("%d1%82%D0%bE%D0%BC%D0%b0%d1%82"))
        assertEquals("\uD83D\uDC68\u200D", decodeUriComponent("%F0%9F%91%A8%E2%80%8D"))
        assertEquals("Школьник утонул в луже и сказал: \"Ouch!\".", decodeUriComponent("%D0%A8%D0%BA%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA%20%D1%83%D1%82%D0%BE%D0%BD%D1%83%D0%BB%20%D0%B2%20%D0%BB%D1%83%D0%B6%D0%B5%20%D0%B8%20%D1%81%D0%BA%D0%B0%D0%B7%D0%B0%D0%BB%3A%20%22Ouch%21%22."))
    }

    @Test
    fun testComponentWithSpaceAsPlus() {
        assertEquals("One, two, three...", decodeUriComponent("One%2C+two%2C+three...", SpaceEncodingMode.PLUS))
    }

    @Test
    fun testIncompleteSequence() {
        assertFailsWith<UriDecoderException> { decodeUriComponent("%") }
        assertFailsWith<UriDecoderException> { decodeUriComponent("%2") }
    }

    @Test
    fun testIllegalCharacters() {
        assertFailsWith<UriDecoderException> { decodeUriComponent("%*^") }
        assertFailsWith<UriDecoderException> { decodeUriComponent("%FP") }
        assertFailsWith<UriDecoderException> { decodeUriComponent("%PF") }
        assertFailsWith<UriDecoderException> { decodeUriComponent("%PP") }
        assertFailsWith<UriDecoderException> { decodeUriComponent("%ШИ") }
    }
}