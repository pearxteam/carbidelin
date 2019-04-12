package ru.pearx.carbidelin.math

private fun pointToString(x: Any, y: Any) = "($x; $y)"

data class FloatPoint(var x: Float, var y: Float) {
    override fun toString(): String = pointToString(x, y)
}

data class DoublePoint(var x: Double, var y: Double) {
    override fun toString(): String = pointToString(x, y)
}

data class BytePoint(var x: Byte, var y: Byte) {
    override fun toString(): String = pointToString(x, y)
}

data class ShortPoint(var x: Short, var y: Short) {
    override fun toString(): String = pointToString(x, y)
}

data class IntPoint(var x: Int, var y: Int) {
    override fun toString(): String = pointToString(x, y)
}

data class LongPoint(var x: Long, var y: Long) {
    override fun toString(): String = pointToString(x, y)
}

data class UBytePoint(var x: UByte, var y: UByte) {
    override fun toString(): String = pointToString(x, y)
}

data class UShortPoint(var x: UShort, var y: UShort) {
    override fun toString(): String = pointToString(x, y)
}

data class UIntPoint(var x: UInt, var y: UInt) {
    override fun toString(): String = pointToString(x, y)
}

data class ULongPoint(var x: ULong, var y: ULong) {
    override fun toString(): String = pointToString(x, y)
}

fun floatPointOf(x: Float, y: Float) = FloatPoint(x, y)
fun doublePointOf(x: Double, y: Double) = DoublePoint(x, y)
fun bytePointOf(x: Byte, y: Byte) = BytePoint(x, y)
fun shortPointOf(x: Short, y: Short) = ShortPoint(x, y)
fun intPointOf(x: Int, y: Int) = IntPoint(x, y)
fun longPointOf(x: Long, y: Long) = LongPoint(x, y)
fun uBytePointOf(x: UByte, y: UByte) = UBytePoint(x, y)
fun uShortPointOf(x: UShort, y: UShort) = UShortPoint(x, y)
fun uIntPointOf(x: UInt, y: UInt) = UIntPoint(x, y)
fun uLongPointOf(x: ULong, y: ULong) = ULongPoint(x, y)