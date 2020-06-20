package org.example.chapter

import org.example.Canvas
import org.example.Color
import org.example.Tuple
import java.io.File
import kotlin.math.round

class Two (e: Environment, private val c: Canvas) {
    private val one = One(e)
    private var p = Projectile(Tuple.point(0.0, 1.0, 0.0), Tuple.vector(1.0, 1.8, 0.0).normalised)

    fun plot(): List<String> {
        do {
            val x = round(p.position.x * 100).toInt()
            val y = c.height - round(p.position.y * 100).toInt()

            c.writePixel(x, y, Color.RED)

            p = one.tick(p)
        }
        while (p.position.y >= 0.0)

        return c.toPpm()
    }
}

fun main(args: Array<String>) {
    val e = Environment(Tuple.vector(0.0, -0.1, 0.0), Tuple.vector(-0.01, 0.0, 0.0))
    val two = Two(e, Canvas(900, 550))

    val plot = two.plot()
    File("plot.ppm").writeText(plot.joinToString("") { it })
}