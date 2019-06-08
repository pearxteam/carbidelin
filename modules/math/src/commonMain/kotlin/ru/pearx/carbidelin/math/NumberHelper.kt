package ru.pearx.carbidelin.math


fun Int.cycle(min: Int, maxInclusive: Int) = ((this - min).rem(maxInclusive - min)) + min
fun Float.isWhole() = this == this.toInt().toFloat()
fun Double.isWhole() = this == this.toInt().toDouble()