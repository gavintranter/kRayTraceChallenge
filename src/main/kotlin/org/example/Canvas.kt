package org.example

class Canvas(val width: Int, val height: Int) {
    private val canvas = HashMap<Pair<Int, Int>, Color>()

    fun getPixelAt(x: Int, y: Int): Color {
        val key = Pair(x, y)
        return canvas.getOrDefault(key, Color.BLACK)
    }

    fun writePixel(x: Int, y: Int, color: Color) {
        canvas[Pair(x, y)] = color
    }

}