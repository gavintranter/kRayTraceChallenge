package org.example

import org.example.Color.Factory.color
import org.example.Tuple.Factory.point
import org.example.Tuple.Factory.vector
import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.math.sqrt

object TuplesSpec: Spek({
    describe("Equals") {
        it("a difference less than 0.00001 is equal") {
            assertEquals(Tuple(1.0, 0.0, 0.0, 1.0), Tuple(1.000009, 0.0, 0.0, 1.0))
        }

        it("a difference greater than equal 0.00001 is not equal") {
            assertNotEquals(Tuple(1.0, 0.0, 0.0, 1.0), Tuple(1.00001, 0.0, 0.0, 1.0))
        }
    }

    describe("Points") {
        describe("tuple with w=1.0") {
            val point by memoized { point(4.3, -4.2, 3.1) }

            it("is a point") {
                assertEquals(true, point.isPoint())
            }

            it("is not a vector") {
                assertEquals(false, point.isVector())
            }
        }
    }

    describe("Vectors") {
        describe("tuple with w=0.0") {
            val vector by memoized { vector(4.3, -4.2, 3.1) }

            it("is a vector") {
                assertEquals(true, vector.isVector())
            }

            it("is not a point") {
                assertEquals(false, vector.isPoint())
            }
        }
    }

    describe("Colours") {
        val color by memoized { color(0.9, 0.6, 0.75) }

        it("is not a vector") {
            assertEquals(false, color.isVector())
        }

        it("is not a point") {
            assertEquals(false, color.isPoint())
        }

        it("is a colour") {
            assertEquals(Color(0.9, 0.6, 0.75, 0.0), color)
        }

        describe("Create colour of one of the defaults") {
            val white = Color.WHITE
            val black = Color.BLACK
            val red = Color.RED
            val green = Color.GREEN
            val blue = Color.BLUE

            it("the default is reused") {
                assertSame(white, color(1.0, 1.0, 1.0))
                assertSame(black, color(0.0, 0.0, 0.0))
                assertSame(red, color(1.0, 0.0, 0.0))
                assertSame(green, color(0.0, 1.0, 0.0))
                assertSame(blue, color(0.0, 0.0, 1.0))
            }
        }
    }

    describe("Operations") {
        val point by memoized { Tuple(3.0, -2.0, 5.0, 1.0) }
        val vector by memoized { Tuple(-2.0, 3.0, 1.0, 0.0) }

        describe("Addition") {
            describe("adding a point and a vector") {
                val result = point + vector

                it("will be a point") {
                    assertEquals(point(1.0, 1.0, 6.0), result)
                }
            }

            describe("adding two vectors") {
                val result = vector + vector

                it("will be a vector") {
                    assertEquals(vector(-4.0, 6.0, 2.0), result)
                }
            }

            describe("adding two points") {
                val result = point + point

                it("will be neither a point nor a vector") {
                    assertEquals(Tuple(6.0, -4.0, 10.0, 2.0), result)
                    assertFalse(result.isVector())
                    assertFalse(result.isPoint())
                }
            }

            describe("adding colours") {
                val c1 = color(0.9, 0.6, 0.75)
                val c2 = color(0.7, 0.1, 0.25)

                val result = c1 + c2

                it("will be the colour (1.6, 0.7, 1.0") {
                    assertEquals(color(1.6, 0.7, 1.0), result)
                }
            }
        }

        describe("Subtraction") {
            describe("subtracting two points") {
                val result = point - point

                it("will be a vector") {
                    assertEquals(vector(0.0, 0.0, 0.0), result)
                }
            }

            describe("subtract a vector from a point") {
                val result = point - vector

                it("will be a point") {
                    assertEquals(point(5.0, -5.0, 4.0), result)
                }
            }

            describe("subtract a vector from a vector") {
                val result = vector - vector

                it("will be a vector") {
                    assertEquals(vector(0.0, 0.0, 0.0), result)
                }
            }

            describe("subtract point from vector") {
                val result = vector - point

                it("will be neither a point nor vector") {
                    assertEquals(Tuple(-5.0, 5.0, -4.0, -1.0), result)
                    assertFalse(result.isPoint())
                    assertFalse(result.isVector())
                }
            }

            describe("subtracting colours") {
                val c1 = color(0.9, 0.6, 0.75)
                val c2 = color(0.7, 0.1, 0.25)

                val result = c1 - c2

                it("will be the colour (0.2, 0.5, 0.5") {
                    assertEquals(color(0.2, 0.5, 0.5), result)
                }
            }
        }

        describe("Negation") {
            describe("subtracting a vector from the zero vector") {
                val result = Tuple.ZERO_VECTOR - vector

                it("the vector will be negated") {
                    assertEquals(vector(2.0, -3.0, -1.0), result)
                }
            }

            describe("negating a vector") {
                val result = -vector

                it("will give a negated vector") {
                    assertEquals(vector(2.0, -3.0, -1.0), result)
                }
            }
        }

        describe("Multiplication") {
            describe("multiplying by a scalar value") {
                val result = point * 3.5

                it("will create a scaled tuple") {
                    assertEquals(Tuple(10.5, -7.0, 17.5, 3.5), result)
                }
            }

            describe("multiplying colour by a scalar value") {
                val c = color(0.2, 0.3, 0.4)

                val result = c * 2.0

                it("will be the colour (0.4, 0.6, 0.8") {
                    assertEquals(color(0.4, 0.6, 0.8), result)
                }
            }

            describe("multiplying colour by a tuple") {
                val c1 = color(1.0, 0.2, 0.4)
                val c2 = color(0.9, 1.0, 0.1)

                val result = c1 * c2

                it("will be the colour (0.9, 0.2, 0.04") {
                    assertEquals(color(0.9, 0.2, 0.04), result)
                }
            }
        }

        describe("Division") {
            describe("dividing by a scalar") {
                val result = point / 2.0

                it("will create a scaled tuple") {
                    assertEquals(Tuple(1.5, -1.0, 2.5, 0.5), result)
                }
            }
        }

        describe("Magnitude") {
            describe("computing magnitude of vector (1.0, 0.0, 0.0)") {
                val v by memoized { vector(1.0, 0.0, 0.0) }

                it("will be 1") {
                    assertEquals(1.0, v.magnitude)
                }
            }

            describe("computing magnitude of vector (0.0, 1.0, 0.0)") {
                val v by memoized { vector(0.0, 1.0, 0.0) }

                it("will be 1") {
                    assertEquals(1.0, v.magnitude)
                }
            }

            describe("computing magnitude of vector (0.0, 0.0, 1.0)") {
                val v by memoized { vector(0.0, 0.0, 1.0) }

                it("will be 1") {
                    assertEquals(1.0, v.magnitude)
                }
            }

            describe("computing magnitude of vector (1.0, 2.0, 3.0)") {
                val v by memoized { vector(1.0, 2.0, 3.0) }

                it("will be the square root of 14") {
                    assertEquals(sqrt(14.0), v.magnitude)
                }
            }

            describe("computing magnitude of vector (-1.0, -2.0, -3.0)") {
                val v by memoized { vector(-1.0, -2.0, -3.0) }

                it("will be the square root of 14") {
                    assertEquals(sqrt(14.0), v.magnitude)
                }
            }
        }

        describe("Normalisation") {
            describe("normalising vector (4.0, 0.0, 0.0)") {
                val v by memoized { vector(4.0, 0.0, 0.0) }

                it("will give a vector (1.0, 0.0, 0.0") {
                    assertEquals(vector(1.0, 0.0, 0.0), v.normalised)
                }
            }

            describe("normalising vector (1.0, 2.0, 3.0)") {
                val v by memoized { vector(1.0, 2.0, 3.0) }

                it("will give a vector (≈0.26726, ≈0.53452, ≈0.80178") {
                    assertEquals(vector(0.26726, 0.53452, 0.80178), v.normalised)
                }
            }
        }

        describe("Dot Product") {
            describe("of two tuples") {
                val v1 by memoized { vector(1.0, 2.0, 3.0) }
                val v2 by memoized { vector(2.0, 3.0, 4.0) }

                it("give a the scalar 20.0") {
                    assertEquals(20.0, v1 dot v2)
                }
            }
        }

        describe("Cross Product") {
            describe("of two vectors") {
                val v1 by memoized { vector(1.0, 2.0, 3.0) }
                val v2 by memoized { vector(2.0, 3.0, 4.0) }

                it("cross product of v1, v2 will be vector (-1.0, 2.0, -1.0)") {
                    assertEquals(vector(-1.0, 2.0, -1.0), v1 cross v2)
                }

                it("cross product of v2, v1 will be vector (1.0, -2.0, 1.0)") {
                    assertEquals(vector(-1.0, 2.0, -1.0), v1 cross v2)
                }
            }
        }
    }
})