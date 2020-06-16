package org.example

import org.example.Tuple.Factory.vector
import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

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
            val point by memoized { Tuple.point(4.3, -4.2, 3.1) }

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
                assertEquals(false, vector.isPoint())
            }

            it("is not a point") {
                assertEquals(true, vector.isVector())
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
                    assertEquals(Tuple.point(1.0, 1.0, 6.0), result)
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
                    assertEquals(Tuple.point(5.0, -5.0, 4.0), result)
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
        }

        describe("Division") {
            describe("dividing by a scalar") {
                val result = point / 2.0

                it("will create a scaled tuple") {
                    assertEquals(Tuple(1.5, -1.0, 2.5, 0.5), result)
                }
            }
        }
    }
})