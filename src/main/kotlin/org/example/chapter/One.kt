package org.example.chapter

import org.example.Tuple
import org.example.Tuple.Factory.point
import org.example.Tuple.Factory.vector

data class Projectile(val position: Tuple, val velocity: Tuple)
data class Environment(val gravity: Tuple, val wind: Tuple)

class One(private val e: Environment) {
    fun tick(proj: Projectile): Projectile {
        val position = proj.position + proj.velocity
        val velocity = proj.velocity + e.gravity + e.wind

        return Projectile(position, velocity)
    }
}

fun main(args: Array<String>) {
    val e = Environment(vector(0.0, -0.1, 0.0), vector(-0.01, 0.0, 0.0))
    var p = Projectile(point(0.0, 1.0, 0.0), vector(1.0, 1.0, 0.0).normalised)

    val one = One(e)

    var count = 0

    do {
        println("p = ${p.position} <> v = ${p.velocity}")
        p = one.tick(p)
        count++
    }
    while (p.position.y >= 0.0)

    println("Count = $count")
}