package org.example

import kotlin.math.round

class Canvas(val width: Int, val height: Int) {
    constructor(width: Int, height: Int, color: Color) : this(width, height) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                writePixel(x, y, color)
            }
        }
    }

    private val canvas = HashMap<Pair<Int, Int>, Color>()

    private val header by lazy {
        listOf("P3", "$width $height", "255")
    }

    fun getPixelAt(x: Int, y: Int): Color {
        val key = Pair(x, y)
        return canvas.getOrDefault(key, Color.BLACK)
    }

    fun writePixel(x: Int, y: Int, color: Color) {
        canvas[Pair(x, y)] = color
    }

    fun toPpm(): List<String> {
        val pixels = IntRange(0, height - 1).map { y ->
            IntRange(0, width - 1).map {x ->
                getPixelAt(x, y)}.joinToString(" ") { mapColor(it) }
        }

        return header + pixels
    }

    private fun mapColor(c: Color): String {
        val color = c * 255.0
        val r = round(clamp(color.r)).toInt()
        val g = round(clamp(color.g)).toInt()
        val b = round(clamp(color.b)).toInt()

        return "$r $g $b"
    }

    private fun clamp(n: Double) = n.coerceAtLeast(0.0).coerceAtMost(255.0)
}