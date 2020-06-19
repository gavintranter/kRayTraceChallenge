package org.example

import org.example.Color.Factory.color
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object CanvasSpec: Spek({
    describe("Canvas") {
        describe("creating a canvas") {
            val c = Canvas(10, 20)

            it("will colour all pixels black (0.0, 0.0, 0.0)") {
                for(x in 0 until c.width) {
                    for (y in 0 until c.height) {
                        assertEquals(Color.BLACK, c.getPixelAt(x, y))
                    }
                }
            }
        }

        describe("writing pixels to a canvas") {
            val canvas = Canvas(10, 10)

            canvas.writePixel(2, 3, Color.RED)

            it("pixel 2, 3 will be red") {
                assertEquals(Color.RED, canvas.getPixelAt(2, 3))
            }
        }

        describe("Creating canvas with given colour") {
            val c = color(1.0, 0.8, 0.6)
            val canvas = Canvas(10, 10, c)

            it("every pixel will be colour (1.0, 0.8, 0.6") {
                for(x in 0 until canvas.width) {
                    for (y in 0 until canvas.height) {
                        assertEquals(c, canvas.getPixelAt(x, y))
                    }
                }
            }
        }

        describe("PPM output") {
            describe("Constructing the PPM header") {
                val canvas = Canvas(5, 3)
                val result = canvas.toPpm()

                it("header (line 1 - 3) will be P3\n5 3\n255") {
                    assertEquals("P3", result[0])
                    assertEquals("5 3", result[1])
                    assertEquals("255", result[2])
                }
            }

            describe("Constructing the PPM pixel data") {
                val canvas = Canvas(5, 3)
                val c1 = color(1.5, 0.0, 0.0)
                val c2 = color(0.0, 0.5, 0.0)
                val c3 = color(-0.5, 0.0, 1.0)

                canvas.writePixel(0, 0, c1)
                canvas.writePixel(2, 1, c2)
                canvas.writePixel(4, 2, c3)

                val result = canvas.toPpm()

                it("data lines (4 - 6) will be\n" +
                        "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0" +
                        "0 0 0 0 0 0 0 128 0 0 0 0 0 0 0" +
                        "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255"
                ) {
                    assertEquals("255 0 0 0 0 0 0 0 0 0 0 0 0 0 0", result[3])
                    assertEquals("0 0 0 0 0 0 0 128 0 0 0 0 0 0 0", result[4])
                    assertEquals("0 0 0 0 0 0 0 0 0 0 0 0 0 0 255", result[5])
                }
            }
        }
    }
})