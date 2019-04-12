package ru.pearx.carbidelin.colors

inline class Color(val argb: Int) {
    val alpha: Int
        get() = argb ushr ALPHA_SHIFT and MASK

    val red: Int
        get() = argb ushr RED_SHIFT and MASK

    val green: Int
        get() = argb ushr GREEN_SHIFT and MASK

    val blue: Int
        get() = argb ushr BLUE_SHIFT and MASK

    val alphaCoefficient: Float
        get() = alpha / COEFFICIENT_DIVISOR

    val redCoefficient: Float
        get() = red / COEFFICIENT_DIVISOR

    val greenCoefficient: Float
        get() = green / COEFFICIENT_DIVISOR

    val blueCoefficient: Float
        get() = blue / COEFFICIENT_DIVISOR

    val rgb: Int
        get() = argb and (MASK shl ALPHA_SHIFT).inv()

    companion object {
        const val MASK = 0xFF
        const val ALPHA_SHIFT = 24
        const val RED_SHIFT = 16
        const val GREEN_SHIFT = 8
        const val BLUE_SHIFT = 0
        const val COEFFICIENT_DIVISOR = 255F
        const val MIN_VALUE = 0
        const val MAX_VALUE = 255

        fun ofArgb(argb: Int): Color = Color(argb)

        fun ofArgb(argb: UInt): Color = ofArgb(argb.toInt())

        fun ofRgb(rgb: Int): Color = Color(rgb or (MAX_VALUE shl ALPHA_SHIFT))

        fun ofRgb(rgb: UInt): Color = ofRgb(rgb.toInt())

        fun of(red: Int, green: Int, blue: Int, alpha: Int = MAX_VALUE): Color {
            requireChannelRange(red, "red")
            requireChannelRange(green, "green")
            requireChannelRange(blue, "blue")
            requireChannelRange(alpha, "alpha")

            return Color((alpha shl ALPHA_SHIFT) or
                (red shl RED_SHIFT) or
                (green shl GREEN_SHIFT) or
                (blue shl BLUE_SHIFT))
        }

        private inline fun requireChannelRange(value: Int, channelName: String) = require(value in MIN_VALUE..MAX_VALUE) { "The $channelName value isn't in range of 0..255" }
    }
}