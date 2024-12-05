package util

import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class StringUtilsTest {

	private val stringWithNumbers = "1 lorem,394 | ipsum dolorsit43"

	/*----- numbers in string -----*/

	@Test
	fun allIntsShouldFindAllNumbers() {
		val expected = listOf(1, 394, 43)
		val actual = stringWithNumbers.allInts()
		assertEquals(expected, actual)
	}

	@Test
	fun allIntsWithNoNumbersShouldReturnEmptyList() {
		val stringWithNoNumbers = "lorem ipsum dolor sit amet"
		val expected = emptyList<Int>()
		val actual = stringWithNoNumbers.allInts()
		assertEquals(expected, actual)
	}

	@Test
	fun allLongsShouldFindAllNumbers() {
		val expected = listOf(1L, 394L, 43L)
		val actual = stringWithNumbers.allLongs()
		assertEquals(expected, actual)
	}

	@Test
	fun allLongsWithNoNumbersShouldReturnEmptyList() {
		val stringWithNoNumbers = "lorem ipsum dolor sit amet"
		val expected = emptyList<Long>()
		val actual = stringWithNoNumbers.allLongs()
		assertEquals(expected, actual)
	}

	@Test
	fun firstIntShouldFindFirstNumber() {
		val expected = 1
		val actual = stringWithNumbers.firstInt()
		assertEquals(expected, actual)
	}

	@Test
	fun firstIntWithNoNumbersShouldThrowException() {
		val stringWithNoNumbers = "lorem ipsum dolor sit amet"
		assertThrows<NoSuchElementException> {
			stringWithNoNumbers.firstInt()
		}
	}

	/*----- input parsing -----*/

	@Test
	fun sectionsShouldSplitStringByEmptyLines() {
		val stringWithSections = "section1\n\nsection2\n\nsection3"
		val expected = listOf("section1", "section2", "section3")
		val actual = stringWithSections.sections()
		assertEquals(expected, actual)
	}

	@Test
	fun sectionsWithNoEmptyLinesShouldReturnListWithOneElement() {
		val stringWithNoSections = "section1\nsection2"
		val expected = listOf("section1\nsection2")
		val actual = stringWithNoSections.sections()
		assertEquals(expected, actual)
	}
}
