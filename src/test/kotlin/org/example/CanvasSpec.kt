package org.example

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

        describe("PPM output") {
            describe("Constructing the PPM header") {
                val canvas = Canvas(5, 3)

                val header = canvas.toPpm()

                it("header will be P3\n5 3\n255") {
                    assertEquals("P3\n5 3\n255", header)
                }
            }
        }
    }
})