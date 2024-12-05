package util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CollectionUtilsTest {

	private val list = listOf(1, 2, 3)
	private val emptyList = emptyList<Int>()

	/*------ pairwise ------*/

	@Test
	fun assertPairwiseWorks() {
		val expected = listOf(1 to 2, 1 to 3, 2 to 3)
		assertEquals(expected, list.pairwise())
	}

	@Test
	fun assertPairwiseWithEmptyListWorks() {
		assertEquals(emptyList, emptyList.pairwise())
	}

	@Test
	fun assertPairwiseWithTransformWorks() {
		val expected = listOf(3, 4, 5)
		assertEquals(expected, list.pairwise { a, b -> a + b })
	}

	/*------ middle ------*/

	@Test
	fun assertMiddleWorks() {
		assertEquals(2, list.middle())
	}

	@Test
	fun assertMiddleWithEmptyListThrowsError() {
		assertThrows<IllegalStateException> { emptyList.middle() }
	}

	@Test
	fun assertMiddleWithEvenSizeListThrowsError() {
		assertThrows<IllegalStateException> { listOf(1, 2).middle() }
	}
}
