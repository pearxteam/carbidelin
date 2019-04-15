package ru.pearx.carbidelin.math


fun Int.cycle(min: Int, maxInclusive: Int) = ((this - min).rem(maxInclusive - min)) + min