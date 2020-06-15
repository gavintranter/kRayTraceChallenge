package org.example

import kotlin.math.abs

data class Tuple(val x: Double, val y: Double, val z: Double, val w: Double) {
    private val EPSILON = 0.00001

    fun isPoint(): Boolean {
        return w > 0.0
    }

    fun isVector(): Boolean {
        return !isPoint()
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
        fun point(x: Double, y: Double, z: Double): Tuple {
            return Tuple(x, y, z, 1.0)
        }

        fun vector(x: Double, y: Double, z: Double): Tuple {
            return Tuple(x, y, z, 0.0)
        }
    }
}