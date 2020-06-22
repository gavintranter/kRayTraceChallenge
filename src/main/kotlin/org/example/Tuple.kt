package org.example

import kotlin.math.abs
import kotlin.math.sqrt

typealias Point = Tuple
typealias Vector = Tuple

data class Tuple(val x: Double, val y: Double, val z: Double, val w: Double) {
    private val EPSILON = 0.00001

    val magnitude: Double by lazy {
        sqrt((x * x) + (y * y) + (z * z) + (w * w))
    }

    val normalised: Tuple by lazy {
        Tuple((x/magnitude), (y/magnitude), (z/magnitude), (w/magnitude))
    }

    fun isPoint(): Boolean {
        return equal(w, 1.0)
    }

    fun isVector(): Boolean {
        return equal(w, 0.0)
    }

    private fun equal(a: Double, b: Double) = abs(a - b) < EPSILON

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tuple

        if (!equal(x, other.x)) return false
        if (!equal(y, other.y)) return false
        if (!equal(z, other.z)) return false
        if (!equal(w, other.w)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + w.hashCode()
        return result
    }

    companion object Factory {
        val ZERO_VECTOR = Tuple(0.0, 0.0, 0.0, 0.0)

        fun point(x: Double, y: Double, z: Double): Tuple {
            return Point(x, y, z, 1.0)
        }

        fun vector(x: Double, y: Double, z: Double): Tuple {
            return Vector(x, y, z, 0.0)
        }
    }

    operator fun plus(other: Tuple): Tuple {
        val dx = x + other.x
        val dy = y + other.y
        val dz = z + other.z
        val dw = w + other.w

        return Tuple(dx, dy, dz, dw)
    }

    operator fun minus(other: Tuple): Tuple {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        val dw = w - other.w

        return Tuple(dx, dy, dz, dw)
    }

    operator fun times(scalar: Double): Tuple {
        val dx = x * scalar
        val dy = y * scalar
        val dz = z * scalar
        val dw = w * scalar

        return Tuple(dx, dy, dz, dw)
    }

    operator fun div(scalar: Double): Tuple {
        val dx = x / scalar
        val dy = y / scalar
        val dz = z / scalar
        val dw = w / scalar

        return Tuple(dx, dy, dz, dw)
    }

    operator fun unaryMinus(): Tuple {
        return ZERO_VECTOR - this
    }

    infix fun dot(other: Tuple): Double {
        val dx = x * other.x
        val dy = y * other.y
        val dz = z * other.z
        val dw = w * other.w

        return dx + dy + dz + dw
    }

    infix fun cross(other: Tuple): Tuple {
        val dx = (y * other.z) - (z * other.y)
        val dy = (z * other.x) - (x * other.z)
        val dz = (x * other.y) - (y * other.x)

        return vector(dx, dy, dz)
    }
}

data class Color(val r: Double, val g: Double, val b: Double, val a: Double) {
    private val tuple = Tuple(r, g, b, a)

    fun isPoint(): Boolean {
        return false
    }

    fun isVector(): Boolean {
        return false
    }

    companion object Factory {
        val BLACK = Color(0.0, 0.0, 0.0, 0.0)
        val WHITE = Color(1.0, 1.0, 1.0, 0.0)
        val RED = Color(1.0, 0.0, 0.0, 0.0)
        val GREEN = Color(0.0, 1.0, 0.0, 0.0)
        val BLUE = Color(0.0, 0.0, 1.0, 0.0)

        fun color(r: Double, g: Double, b: Double): Color {
            return when {
                (r == 1.0 && g == 1.0 && b == 1.0) -> WHITE
                (r == 0.0 && g == 0.0 && b == 0.0) -> BLACK
                (r == 1.0 && g == 0.0 && b == 0.0) -> RED
                (r == 0.0 && g == 1.0 && b == 0.0) -> GREEN
                (r == 0.0 && g == 0.0 && b == 1.0) -> BLUE
                else -> Color(r, g, b, 0.0)
            }
        }
    }

    operator fun plus(other: Color): Color {
        val result = tuple + other.tuple
        return Color(result.x, result.y, result.z, result.w)
    }

    operator fun minus(other: Color): Color {
        val result = tuple - other.tuple
        return Color(result.x, result.y, result.z, result.w)
    }

    operator fun times(scalar: Double): Color {
        val result = tuple * scalar
        return Color(result.x, result.y, result.z, result.w)
    }

    operator fun times(other: Color): Color {
        val dr = r * other.r
        val dg = g * other.g
        val db = b * other.b

        return Color(dr, dg, db, a)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Color

        return tuple == other.tuple
    }

    override fun hashCode(): Int {
        return tuple.hashCode()
    }

}