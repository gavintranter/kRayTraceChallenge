package org.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TuplesSpec: Spek({
    describe("equals") {
        it("a difference less than 0.00001 is equal") {
            assertEquals(Tuple(1.0, 0.0, 0.0, 1.0), Tuple(1.000009, 0.0, 0.0, 1.0))
        }

        it("a difference greater than equal 0.00001 is not equal") {
            assertNotEquals(Tuple(1.0, 0.0, 0.0, 1.0), Tuple(1.00001, 0.0, 0.0, 1.0))
        }
    }

    describe("points") {
        describe("tuple with w=1.0") {
            val point by memoized { Tuple(4.3, -4.2, 3.1, 1.0) }

            it("is a point") {
                assertEquals(point.isPoint(), true)
            }

            it("is not a vector") {
                assertEquals(point.isVector(), false)
            }

            it("can be created with a point function") {
                assertEquals(Tuple.point(4.3, -4.2, 3.1), point)
            }
        }
    }

    describe("vectors") {
        describe("tuple with w=0.0") {
            val vector by memoized { Tuple(4.3, -4.2, 3.1, 0.0) }

            it("is a vector") {
                assertEquals(vector.isPoint(), false)
            }

            it("is not a point") {
                assertEquals(vector.isVector(), true)
            }

            it("can be created with a vector function") {
                assertEquals(Tuple.vector(4.3, -4.2, 3.1), vector)
            }
        }
    }
})