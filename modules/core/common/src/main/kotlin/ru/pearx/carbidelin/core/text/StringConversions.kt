/*
 *  Copyright © 2018 mrAppleXZ
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package ru.pearx.carbidelin.core.text

const val HEXADECIMAL_RADIX = 16
const val BINARY_RADIX = 2

fun Byte.toHexString(pad: Boolean = false) = toUByte().toHexString(pad)
fun Byte.toBinaryString(pad: Boolean = false) = toUByte().toBinaryString(pad)

fun Short.toHexString(pad: Boolean = false) = toUShort().toHexString(pad)
fun Short.toBinaryString(pad: Boolean = false) = toUShort().toBinaryString(pad)

fun Int.toHexString(pad: Boolean = false) = toUInt().toHexString(pad)
fun Int.toBinaryString(pad: Boolean = false) = toUInt().toBinaryString(pad)

fun Long.toHexString(pad: Boolean = false) = toULong().toHexString(pad)
fun Long.toBinaryString(pad: Boolean = false) = toULong().toBinaryString(pad)

fun UByte.toHexString(pad: Boolean = false) = numToString(toString(HEXADECIMAL_RADIX), 2, pad)
fun UByte.toBinaryString(pad: Boolean = false) = numToString(toString(BINARY_RADIX), 8, pad)

fun UShort.toHexString(pad: Boolean = false) = numToString(toString(HEXADECIMAL_RADIX), 4, pad)
fun UShort.toBinaryString(pad: Boolean = false) = numToString(toString(BINARY_RADIX), 16, pad)

fun UInt.toHexString(pad: Boolean = false) = numToString(toString(HEXADECIMAL_RADIX), 8, pad)
fun UInt.toBinaryString(pad: Boolean = false) = numToString(toString(BINARY_RADIX), 32, pad)

fun ULong.toHexString(pad: Boolean = false) = numToString(toString(HEXADECIMAL_RADIX), 16, pad)
fun ULong.toBinaryString(pad: Boolean = false) = numToString(toString(BINARY_RADIX), 64, pad)

fun ByteArray.toHexString(pad: Boolean = false) = StringBuilder().also { hexStringTo(it, pad) }.toString()

fun ByteArray.hexStringTo(appendable: Appendable, pad: Boolean = false) {
    for (byte in this) {
        appendable.append(byte.toHexString(pad))
    }
}

private fun numToString(value: String, size: Int, pad: Boolean) = if (pad) value.padStart(size, '0') else value