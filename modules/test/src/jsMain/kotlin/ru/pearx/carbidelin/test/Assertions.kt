package ru.pearx.carbidelin.test

import kotlin.math.abs
import kotlin.test.assertTrue

actual fun assertEquals(expected: Float, actual: Float, delta: Float, message: String?) {
    assertTrue(abs(expected - actual) <= delta, StringBuilder().apply {
        if(message != null) {
            append(message)
            append(". ")
        }
        append("Expected <").append(expected).append(">, actual <").append(actual).append(">.")
    }.toString())
}