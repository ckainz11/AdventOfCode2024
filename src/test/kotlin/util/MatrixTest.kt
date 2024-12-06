package util

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class MatrixTest {

	private val emptyMatrix = listOf(emptyList<Int>())
	private val matrix = matrixOf(
		listOf(1, 2, 3),
		listOf(4, 5, 6),
		listOf(7, 8, 9)
	)

	@Test
	fun `get should return a value`() {
		assertEquals(1, matrix[Point(0, 0)])
		assertEquals(5, matrix[Point(1, 1)])
		assertEquals(9, matrix[Point(2, 2)])
	}

	@Test
	fun `get with wrong index should throw an exception`() {
		assertThrows<IndexOutOfBoundsException> { matrix[Point(3, 3)] }
	}

	@Test
	fun `set should update a value`() {
		val mutableMatrix = matrix.toMutableMatrix()
		mutableMatrix[Point(0, 0)] = 10
		mutableMatrix[Point(1, 1)] = 20
		mutableMatrix[Point(2, 2)] = 30
		assertEquals(10, mutableMatrix[Point(0, 0)])
		assertEquals(20, mutableMatrix[Point(1, 1)])
		assertEquals(30, mutableMatrix[Point(2, 2)])
	}

	@Test
	fun `set with wrong index should throw an exception`() {
		assertThrows<IndexOutOfBoundsException> { matrix[Point(3, 3)] }
	}

	@Test
	fun `getOrElse should return the value if its present`() {
		assertEquals(1, matrix.getOrElse(Point(0, 0), 0))
		assertEquals(5, matrix.getOrElse(Point(1, 1), 0))
		assertEquals(9, matrix.getOrElse(Point(2, 2), 0))
	}

	@Test
	fun `getOrElse should return default if the value is not present`() {
		assertEquals(0, matrix.getOrElse(Point(3, 3), 0))
		assertEquals(0, matrix.getOrElse(Point(0, 3), 0))
		assertEquals(0, matrix.getOrElse(Point(3, 0), 0))
	}

	/*----- subMatrix -----*/

	@Test
	fun `subMatrix should return a sub matrix`() {
		val sub = matrix.subMatrix(2, 2, Point(1, 1))
		assertEquals(matrixOf(listOf(5, 6), listOf(8, 9)), sub)
	}

	@Test
	fun `subMatrix over the whole matrix should return the same matrix`() {
		val sub = matrix.subMatrix(3, 3, Point(0, 0))
		assertEquals(matrix, sub)
	}

	@Test
	fun `subMatrix with a single row should return a matrix with a single row`() {
		val sub = matrix.subMatrix(1, 3, Point(0, 1))
		assertEquals(matrixOf(listOf(4, 5, 6)), sub)
	}

	/*----- MutableMatrix -----*/

	@Test
	fun `mutableMatrix should be able to update values`() {
		val mutableMatrix = matrix.toMutableMatrix()
		mutableMatrix[Point(0, 0)] = 10
		mutableMatrix[Point(1, 1)] = 20
		mutableMatrix[Point(2, 2)] = 30
		assertEquals(10, mutableMatrix[Point(0, 0)])
		assertEquals(20, mutableMatrix[Point(1, 1)])
		assertEquals(30, mutableMatrix[Point(2, 2)])
	}

	/*----- Columns and Rows -----*/

	@Test
	fun `getColumn should return a column`() {
		assertEquals(listOf(1, 4, 7), matrix.getColumn(0))
		assertEquals(listOf(2, 5, 8), matrix.getColumn(1))
		assertEquals(listOf(3, 6, 9), matrix.getColumn(2))
	}

	@Test
	fun `columns should return the columns of the matrix`() {
		assertEquals(matrixOf(listOf(1, 4, 7), listOf(2, 5, 8), listOf(3, 6, 9)), matrix.columns())
	}

	@Test
	fun `xRange should return the range of x values`() {
		assertEquals(0..2, matrix.xRange())
	}

	@Test
	fun `yRange should return the range of y values`() {
		assertEquals(0..2, matrix.yRange())
	}

	/*----- Stream Operations -----*/

	@Test
	fun `mapMatrix should apply the transformation to each element`() {
		val mapped = matrix.mapMatrix { it * 2 }
		assertEquals(matrixOf(listOf(2, 4, 6), listOf(8, 10, 12), listOf(14, 16, 18)), mapped)
	}

	@Test
	fun `mapMatrixIndexed should apply the transformation to each element with its index`() {
		val mapped = matrix.mapMatrixIndexed { p, v -> p.x + p.y + v }
		assertEquals(matrixOf(listOf(1, 3, 5), listOf(5, 7, 9), listOf(9, 11, 13)), mapped)
	}

	@Test
	fun `mapMatrixIndexedNotNull should apply the transformation to each element with its index and filter nulls`() {
		val mapped = matrix.mapMatrixIndexedNotNull { p, v -> if (v % 2 == 0) p.x + p.y + v else null }
		assertEquals(listOf(3, 5, 9, 11), mapped)
	}

	@Test
	fun `matrixForEachIndexed should apply the action to each element with its index`() {
		val result = mutableListOf<Pair<Point, Int>>()
		matrix.matrixForEachIndexed { p, v -> result.add(p to v) }
		assertEquals(
			listOf(
				Point(0, 0) to 1, Point(1, 0) to 2, Point(2, 0) to 3,
				Point(0, 1) to 4, Point(1, 1) to 5, Point(2, 1) to 6,
				Point(0, 2) to 7, Point(1, 2) to 8, Point(2, 2) to 9
			), result
		)
	}

	@Test
	fun `matrixIndexOfFirst should return the index of the first element that matches the predicate`() {
		val index = matrix.matrixIndexOfFirst { it % 2 == 0 }
		assertEquals(Point(1, 0), index)
	}

	@Test
	fun `matrixIndexOfFirst should return -1 if no element matches the predicate`() {
		val index = matrix.matrixIndexOfFirst { it > 10 }
		assertEquals(Point(-1, -1), index)
	}

	@Test
	fun `matrixMax should return the maximum value in the matrix`() {
		assertEquals(9, matrix.matrixMax())
	}

	@Test
	fun `matrixMax should throw null pointer exception if the matrix is empty`() {
		assertThrows<NullPointerException> { emptyMatrix.matrixMax() }
	}

	@Test
	fun `matrixMin should return the minimum value in the matrix`() {
		assertEquals(1, matrix.matrixMin())
	}

	@Test
	fun `matrixMin should throw null pointer exception if the matrix is empty`() {
		assertThrows<NullPointerException> { emptyMatrix.matrixMin() }
	}

	@Test
	fun `matrixSumOf should return the sum of all elements in the matrix`() {
		assertEquals(45, matrix.matrixSumOf { it })
	}

	@Test
	fun `matrixCount should return the number of elements that match the predicate`() {
		assertEquals(5, matrix.matrixCount { it % 2 == 1 })
	}

	/*----- Rotating -----*/

	@Test
	fun `rotated should rotate the matrix 90 degrees clockwise`() {
		val rotated = matrix.rotated()
		assertEquals(matrixOf(listOf(7, 4, 1), listOf(8, 5, 2), listOf(9, 6, 3)), rotated)
	}

	@Test
	fun `rotated with times = 2 should rotate the matrix 180 degrees clockwise`() {
		val rotated = matrix.rotated(2)
		assertEquals(matrixOf(listOf(9, 8, 7), listOf(6, 5, 4), listOf(3, 2, 1)), rotated)
	}

	@Test
	fun `rotated with times = 3 should rotate the matrix 270 degrees clockwise`() {
		val rotated = matrix.rotated(3)
		assertEquals(matrixOf(listOf(3, 6, 9), listOf(2, 5, 8), listOf(1, 4, 7)), rotated)
	}

	@Test
	fun `rotated with times = 4 should return the same matrix`() {
		val rotated = matrix.rotated(4)
		assertEquals(matrix, rotated)
	}

	@Test
	fun `rotated with times = 0 should return the same matrix`() {
		val rotated = matrix.rotated(0)
		assertEquals(matrix, rotated)
	}

	/*----- String -----*/

	@Test
	fun `matrixToString should return a string representation of the matrix`() {
		val expected = "1, 2, 3\n4, 5, 6\n7, 8, 9"
		assertEquals(expected, matrix.matrixToString())
	}

	@Test
	fun `matrixToString with a custom separator should return a string representation of the matrix`() {
		val expected = "1 - 2 - 3\n4 - 5 - 6\n7 - 8 - 9"
		assertEquals(expected, matrix.matrixToString(" - "))
	}

}
