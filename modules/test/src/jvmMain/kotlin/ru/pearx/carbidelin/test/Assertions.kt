package ru.pearx.carbidelin.test

import org.junit.jupiter.api.Assertions

actual fun assertEquals(expected: Float, actual: Float, delta: Float, message: String?) = Assertions.assertEquals(expected, actual, delta, message)