package util

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class RangeUtilsTest {

	private val bigRange = 1..10
	private val smallRange = 5..7
	private val adjacentRange = 11..15
	private val negativeRange = -5..0

	/*----- overlaps -----*/

	@Test
	fun testOverlaps() {
		assertTrue(bigRange overlaps smallRange)
		assertTrue(smallRange overlaps bigRange)
		assertFalse(bigRange overlaps adjacentRange)
		assertFalse(adjacentRange overlaps bigRange)
		assertFalse(bigRange overlaps negativeRange)
		assertFalse(negativeRange overlaps bigRange)
	}

	/*----- containsRange -----*/

	@Test
	fun testContainsRange() {
		assertTrue(bigRange containsRange smallRange)
		assertFalse(smallRange containsRange bigRange)
		assertFalse(bigRange containsRange adjacentRange)
		assertFalse(adjacentRange containsRange bigRange)
		assertFalse(bigRange containsRange negativeRange)
		assertFalse(negativeRange containsRange bigRange)
	}

	/*----- adjoint -----*/

	@Test
	fun testAdjoint() {
		assertTrue(bigRange adjoint adjacentRange)
		assertTrue(adjacentRange adjoint bigRange)
		assertFalse(bigRange adjoint smallRange)
		assertFalse(smallRange adjoint bigRange)
		assertTrue(bigRange adjoint negativeRange)
		assertTrue(negativeRange adjoint bigRange)
	}

}
