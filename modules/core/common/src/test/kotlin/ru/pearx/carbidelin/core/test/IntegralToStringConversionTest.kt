/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.core.test

import ru.pearx.carbidelin.core.text.toBinaryString
import ru.pearx.carbidelin.core.text.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class IntegralToStringConversionTest
{
    @Test
    fun byteToString()
    {
        assertEquals("ff", UByte.MAX_VALUE.toHexString())
        assertEquals("e", 14.toByte().toHexString())
        assertEquals("ff", UByte.MAX_VALUE.toHexString(true))
        assertEquals("0e", 14.toByte().toHexString(true))

        assertEquals("11111111", UByte.MAX_VALUE.toBinaryString())
        assertEquals("1110", 14.toByte().toBinaryString())
        assertEquals("00001110", 14.toByte().toBinaryString(true))
    }

    @Test
    fun shortToString()
    {
        assertEquals("ffff", UShort.MAX_VALUE.toHexString())
        assertEquals("101", 257.toShort().toHexString())
        assertEquals("0101", 257.toShort().toHexString(true))

        assertEquals("1111111111111111", UShort.MAX_VALUE.toBinaryString())
        assertEquals("100000001", 257.toShort().toBinaryString())
        assertEquals("0000000100000001", 257.toShort().toBinaryString(true))
    }

    @Test
    fun intToString()
    {
        assertEquals("ffffffff", UInt.MAX_VALUE.toHexString())
        assertEquals("1e3a4", 123812.toHexString())
        assertEquals("0001e3a4", 123812.toHexString(true))

        assertEquals("11111111111111111111111111111111", UInt.MAX_VALUE.toBinaryString())
        assertEquals("11110001110100100", 123812.toBinaryString())
        assertEquals("00000000000000011110001110100100", 123812.toBinaryString(true))
    }

    @Test
    fun longToString()
    {
        assertEquals("ffffffffffffffff", ULong.MAX_VALUE.toHexString())
        assertEquals("1cd3ca6e8a", 123812343434L.toHexString())
        assertEquals("0000001cd3ca6e8a", 123812343434L.toHexString(true))

        assertEquals("1111111111111111111111111111111111111111111111111111111111111111", ULong.MAX_VALUE.toBinaryString())
        assertEquals("1110011010011110010100110111010001010", 123812343434L.toBinaryString())
        assertEquals("0000000000000000000000000001110011010011110010100110111010001010", 123812343434L.toBinaryString(true))
    }
}