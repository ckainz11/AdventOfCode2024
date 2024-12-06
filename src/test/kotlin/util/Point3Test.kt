package util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Point3Test {

	/*----- arithmetic operations -----*/

	@Test
	fun testPlus() {
		val point = Point3(1, 2, 3)
		val other = Point3(4, 5, 6)
		val expected = Point3(5, 7, 9)
		val actual = point + other
		assertEquals(expected, actual)
	}

	@Test
	fun testMinus() {
		val point = Point3(1, 2, 3)
		val other = Point3(4, 5, 6)
		val expected = Point3(-3, -3, -3)
		val actual = point - other
		assertEquals(expected, actual)
	}

	@Test
	fun testTimes() {
		val point = Point3(1, 2, 3)
		val n = 3
		val expected = Point3(3, 6, 9)
		val actual = point * n
		assertEquals(expected, actual)
	}

	/*----- neighbors -----*/

	@Test
	fun testNeighbors() {
		val point = Point3(1, 2, 3)
		val expected = listOf(
			Point3(2, 2, 3), Point3(0, 2, 3),
			Point3(1, 3, 3), Point3(1, 1, 3),
			Point3(1, 2, 4), Point3(1, 2, 2),
		)
		val actual = point.neighbors()
		assertEquals(expected, actual)
	}
}
