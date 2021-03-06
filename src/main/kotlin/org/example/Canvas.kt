package org.example

import kotlin.math.round

class Canvas(val width: Int, val height: Int) {
    private data class Coordinate(val x: Int, val y: Int)

    constructor(width: Int, height: Int, color: Color) : this(width, height) {
        (0 until width).forEach { x ->
            (0 until height).forEach { y ->
                writePixel(x, y, color)
            }
        }
    }

    private val canvas = HashMap<Coordinate, Color>()

    private val header: List<String> by lazy {
        listOf("P3\n", "$width $height\n", "255\n")
    }

    fun getPixelAt(x: Int, y: Int): Color {
        return canvas.getOrDefault(Coordinate(x, y), Color.BLACK)
    }

    fun writePixel(x: Int, y: Int, color: Color) {
        if (x !in 0 until width || y !in 0 until height) {
            return
        }
        canvas[Coordinate(x, y)] = color
    }

    fun toPpm(): List<String> {
        val pixels = (0 until height).map { y ->
                (0 until width).map { x -> getPixelAt(x, y) }
                .map { mapColor(it) }
                .flatten()
            }
            .map { toPpmLines(it) }
            .flatten()

        return header + pixels
    }

    private fun mapColor(c: Color): List<Int> {
        val color = c * 255.0
        val r = round(clamp(color.r)).toInt()
        val g = round(clamp(color.g)).toInt()
        val b = round(clamp(color.b)).toInt()

        return listOf(r, g, b)
    }

    private fun clamp(channel: Double) = channel.coerceAtLeast(0.0).coerceAtMost(255.0)

    private fun toPpmLines(row: List<Int>): List<String> {
        return row.fold("") { acc, value -> joinWithoutSplittingValues(acc, value) }
            .split("\n")
            .map { it.trim() }
            .map { it + "\n" }
    }

    private fun joinWithoutSplittingValues(acc: String, value: Int): String {
        return if (acc.isEmpty()) {
            acc + value
        } else {
            val remainingLength = 70 - (acc.length - acc.lastIndexOf("\n"))

            if (remainingLength <= value.toString().length) {
                acc + "\n" + value.toString()
            } else {
                acc + " " + value.toString()
            }
        }
    }
}