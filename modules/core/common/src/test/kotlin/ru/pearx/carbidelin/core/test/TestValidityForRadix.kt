/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.core.test

import ru.pearx.carbidelin.core.text.isValidForRadix
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestValidityForRadix
{
    @Test
    fun test0()
    {
        assertFalse('0'.isValidForRadix(0))
        assertFalse('1'.isValidForRadix(0))
        assertFalse('9'.isValidForRadix(0))
        assertFalse('A'.isValidForRadix(0))
        assertFalse('F'.isValidForRadix(0))
        assertFalse('Z'.isValidForRadix(0))
        assertFalse('='.isValidForRadix(0))
    }

    @Test
    fun test1()
    {
        assertTrue('0'.isValidForRadix(1))
        assertFalse('1'.isValidForRadix(1))
        assertFalse('9'.isValidForRadix(1))
        assertFalse('A'.isValidForRadix(1))
        assertFalse('F'.isValidForRadix(1))
        assertFalse('Z'.isValidForRadix(1))
        assertFalse('='.isValidForRadix(1))
    }

    @Test
    fun test10()
    {
        assertTrue('0'.isValidForRadix(10))
        assertTrue('1'.isValidForRadix(10))
        assertTrue('9'.isValidForRadix(10))
        assertFalse('A'.isValidForRadix(10))
        assertFalse('F'.isValidForRadix(10))
        assertFalse('Z'.isValidForRadix(10))
        assertFalse('='.isValidForRadix(10))
    }

    @Test
    fun test16()
    {

        assertTrue('0'.isValidForRadix(16))
        assertTrue('1'.isValidForRadix(16))
        assertTrue('9'.isValidForRadix(16))
        assertTrue('A'.isValidForRadix(16))
        assertTrue('F'.isValidForRadix(16))
        assertFalse('Z'.isValidForRadix(16))
        assertFalse('='.isValidForRadix(16))
    }

    @Test
    fun test36()
    {
        assertTrue('0'.isValidForRadix(36))
        assertTrue('1'.isValidForRadix(36))
        assertTrue('9'.isValidForRadix(36))
        assertTrue('A'.isValidForRadix(36))
        assertTrue('F'.isValidForRadix(36))
        assertTrue('Z'.isValidForRadix(36))
        assertFalse('='.isValidForRadix(36))
    }
}