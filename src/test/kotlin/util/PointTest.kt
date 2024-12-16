package util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class PointTest {

	/*----- rotating -----*/

	@Test
	fun testRotateClockwiseCardinalDown() {
		val point = Point(0, 2)
		val expected = Point(-2, 0)
		val actual = point.rotateClockwise()
		assertEquals(expected, actual)
	}

	@Test
	fun testRotateClockwiseCardinalLeft() {
		val point = Point(-2, 0)
		val expected = Point(0, -2)
		val actual = point.rotateClockwise()
		assertEquals(expected, actual)
	}

	@Test
	fun testRotateClockwiseCardinalUp() {
		val point = Point(0, -2)
		val expected = Point(2, 0)
		val actual = point.rotateClockwise()
		assertEquals(expected, actual)
	}

	@Test
	fun testRotateClockwiseCardinalRight() {
		val point = Point(2, 0)
		val expected = Point(0, 2)
		val actual = point.rotateClockwise()
		assertEquals(expected, actual)
	}

	@Test
	fun testRotateCounterClockwiseCardinalDown() {
		val point = Point(0, 2)
		val expected = Point(2, 0)
		val actual = point.rotateCounterClockwise()
		assertEquals(expected, actual)
	}

	@Test
	fun testRotateCounterClockwiseCardinalLeft() {
		val point = Point(-2, 0)
		val expected = Point(0, 2)
		val actual = point.rotateCounterClockwise()
		assertEquals(expected, actual)
	}

	@Test
	fun testRotateCounterClockwiseCardinalUp() {
		val point = Point(0, -2)
		val expected = Point(-2, 0)
		val actual = point.rotateCounterClockwise()
		assertEquals(expected, actual)
	}

	@Test
	fun testRotateCounterClockwiseCardinalRight() {
		val point = Point(2, 0)
		val expected = Point(0, -2)
		val actual = point.rotateCounterClockwise()
		assertEquals(expected, actual)
	}

	/* ----- arithmetic operations -----*/

	@Test
	fun testPlus() {
		val point = Point(1, 2)
		val other = Point(3, 4)
		val expected = Point(4, 6)
		val actual = point + other
		assertEquals(expected, actual)
	}

	@Test
	fun testMinus() {
		val point = Point(1, 2)
		val other = Point(3, 4)
		val expected = Point(-2, -2)
		val actual = point - other
		assertEquals(expected, actual)
	}

	@Test
	fun testTimes() {
		val point = Point(1, 2)
		val n = 3
		val expected = Point(3, 6)
		val actual = point * n
		assertEquals(expected, actual)
	}

	@Test
	fun testRem() {
		val point = Point(5, 5)
		val other = Point(3, 3)
		val expected = Point(2, 2)
		val actual = point % other
		assertEquals(expected, actual)
	}

	/*----- distance -----*/

	@Test
	fun testManhattanDistance() {
		val point = Point(1, 2)
		val other = Point(3, 4)
		val expected = 4
		val actual = point.mDist(other)
		assertEquals(expected, actual)
	}

	@Test
	fun testManhattanDistanceNegative() {
		val point = Point(1, 2)
		val other = Point(-3, -4)
		val expected = 10
		val actual = point.mDist(other)
		assertEquals(expected, actual)
	}

	/*----- inBounds -----*/
	private val grid = emptyMatrixOf(3, 3, 0)

	@Test
	fun `inBoundsOf should return true for point in bounds`() {
		val point = Point(1, 1)
		val actual = point inBoundsOf grid
		assertEquals(true, actual)
	}

	@Test
	fun `inBoundsOf should return false for point out of bounds`() {
		val point = Point(3, 3)
		val actual = point inBoundsOf grid
		assertEquals(false, actual)
	}

	/*----- cardinalNeighbors -----*/

	@Test
	fun `cardinalNeighbors should return all cardinal neighbors`() {
		val point = Point(1, 1)
		val expected = listOf(Point(2, 1), Point(1, 2), Point(0, 1), Point(1, 0))
		val actual = point.cardinalNeighbors()
		assertEquals(expected, actual)
	}

	@Test
	fun `cardinalNeighbors should not return diagonal neighbors`() {
		val point = Point(1, 1)
		val notExpected = listOf(Point(2, 2), Point(0, 0), Point(0, 2), Point(2, 0))
		val actual = point.cardinalNeighbors()
		notExpected.forEach { assertFalse(it in actual) }
	}
}
