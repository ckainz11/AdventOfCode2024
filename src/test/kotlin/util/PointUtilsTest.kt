package util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PointUtilsTest {

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
}
