package ru.pearx.carbidelin.math

import kotlin.math.ceil

fun calculateQuadraticBezierPoint(t: Float, x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float): FloatPoint {
    val omt = 1 - t
    return floatPointOf(omt * (omt * x0 + t * x1) + t * (omt * x1 + t * x2), omt * (omt * y0 + t * y1) + t * (omt * y1 + t * y2))
}

fun calculateQuadraticBezierPoints(deltaT: Float, x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float): Array<FloatPoint> =
    Array(ceil(1 / deltaT).toInt() + 1) { index -> calculateQuadraticBezierPoint(index * deltaT, x0, y0, x1, y1, x2, y2) }