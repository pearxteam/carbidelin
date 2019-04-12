package ru.pearx.carbidelin.colors.test

import ru.pearx.carbidelin.colors.Color
import ru.pearx.carbidelin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ColorTest {
    @Test
    fun testArgbCreation() {
        assertEquals(0xFFFFFFFFU.toInt(), Color.ofArgb(0xFFFFFFFFU).argb)
        assertEquals(0x10214A, Color.ofArgb(0x10214A).argb)
        assertEquals(0, Color.ofArgb(0).argb)
    }

    @Test
    fun testRgbCreation() {
        assertEquals(0xFFFFFFFFU.toInt(), Color.ofRgb(0xFFFFFFFFU).argb)
        assertEquals(0xFFFFFFFFU.toInt(), Color.ofRgb(0xABFFFFFFU).argb)
        assertEquals(0xFF10214AU.toInt(), Color.ofRgb(0x10214A).argb)
        assertEquals(0xFF000000U.toInt(), Color.ofRgb(0).argb)
    }

    @Test
    fun testChannelCreationFails() {
        assertFailsWith<IllegalArgumentException> { Color.of(-1, 0, 0, 0) }
        assertFailsWith<IllegalArgumentException> { Color.of(0, -1, 0, 0) }
        assertFailsWith<IllegalArgumentException> { Color.of(0, 0, -1, 0) }
        assertFailsWith<IllegalArgumentException> { Color.of(0, 0, 0, -1) }

        assertFailsWith<IllegalArgumentException> { Color.of(256, 0, 0, 0) }
        assertFailsWith<IllegalArgumentException> { Color.of(0, 256, 0, 0) }
        assertFailsWith<IllegalArgumentException> { Color.of(0, 0, 256, 0) }
        assertFailsWith<IllegalArgumentException> { Color.of(0, 0, 0, 256) }

        assertFailsWith<IllegalArgumentException> { Color.of(256, 0, 256, 0) }
        assertFailsWith<IllegalArgumentException> { Color.of(0, -1, 0, -1) }
    }

    @Test
    fun testChannelCreation() {
        assertEquals(0xFFFFFFFFU.toInt(), Color.of(255, 255, 255).argb)
        assertEquals(0xFFFFFFFFU.toInt(), Color.of(255, 255, 255, 255).argb)
        assertEquals(0x10214A, Color.of(16, 33, 74, 0).argb)
        assertEquals(0xFF10214AU.toInt(), Color.of(16, 33, 74).argb)
        assertEquals(0xFF000000U.toInt(), Color.of(0, 0, 0).argb)
        assertEquals(0, Color.of(0, 0, 0, 0).argb)
    }

    @Test
    fun testRgb() {
        assertEquals(0xFFFFFF, Color.of(255, 255, 255).rgb)
        assertEquals(0x10214A, Color.of(16, 33, 74, 0).rgb)
        assertEquals(0x10214A, Color.of(16, 33, 74).rgb)
        assertEquals(0, Color.of(0, 0, 0).rgb)
    }

    @Test
    fun testChannels() {
        val colAll255 = Color.of(255, 255, 255)
        assertEquals(255, colAll255.red)
        assertEquals(255, colAll255.green)
        assertEquals(255, colAll255.blue)
        assertEquals(255, colAll255.alpha)

        val col = Color.of(16, 33, 74, 0)
        assertEquals(16, col.red)
        assertEquals(33, col.green)
        assertEquals(74, col.blue)
        assertEquals(0, col.alpha)
    }

    @Test
    fun testChannelCoefficients() {
        val colAll255 = Color.of(255, 255, 255)
        assertEquals(1F, colAll255.redCoefficient, 0.001F)
        assertEquals(1F, colAll255.greenCoefficient, 0.01F)
        assertEquals(1F, colAll255.blueCoefficient, 0.01F)
        assertEquals(1F, colAll255.alphaCoefficient, 0.01F)

        val col = Color.of(16, 33, 74, 0)
        assertEquals(0.062F, col.redCoefficient, 0.01F)
        assertEquals(0.129F, col.greenCoefficient, 0.01F)
        assertEquals(0.29F, col.blueCoefficient, 0.01F)
        assertEquals(0F, col.alphaCoefficient, 0.01F)
    }
}