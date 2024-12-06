package util

import java.lang.IndexOutOfBoundsException

typealias Matrix<T> = List<List<T>>
typealias MutableMatrix<T> = MutableList<MutableList<T>>

fun <T> matrixOf(vararg rows: List<T>): Matrix<T> = List(rows.size) { i -> rows[i] }
fun <T> Matrix<T>.toMutableMatrix(): MutableMatrix<T> = this.map { it.toMutableList() }.toMutableList()
fun <T> Matrix<T>.getColumn(col: Int): List<T> = getCol(this, col)
fun <T> Matrix<T>.columns() = rotated(1)
operator fun <T> Matrix<T>.get(p: Point): T = this[p.y][p.x]
operator fun <T> MutableMatrix<T>.set(p: Point, value: T) {
	this[p.y][p.x] = value
}

fun <T, R> Matrix<T>.mapMatrix(transform: (T) -> R): Matrix<R> = this.map { it.map(transform) }
fun <T, R> Matrix<T>.mapMatrixIndexed(transform: (Point, T) -> R): Matrix<R> =
	this.mapIndexed { y, row -> row.mapIndexed { x, col -> transform(Point(x, y), col) } }

fun <T> Matrix<T>.matrixForEachIndexed(action: (Point, T) -> Unit) {
	this.forEachIndexed { y, row -> row.forEachIndexed { x, col -> action(Point(x, y), col) } }
}

/**
 * Returns index of the first element matching the given [predicate], or -1 if the list does not contain such element.
 */
fun <T> Matrix<T>.matrixIndexOfFirst(predicate: (T) -> Boolean): Point {
	for ((y, row) in this.withIndex()) {
		for ((x, value) in row.withIndex()) {
			if (predicate(value))
				return Point(x, y)
		}
	}
	return Point(-1, -1)
}

fun <T> Matrix<T>.subMatrix(rows: Int, cols: Int, index: Point): Matrix<T> {
	val sub = mutableListOf<List<T>>()
	for (y in index.y..<index.y + rows) {
		sub.add(this[y].subList(index.x, index.x + cols))
	}
	return sub
}

fun <T> Matrix<T>.matrixToString(): String = this.joinToString("\n") { it.joinToString(", ") }
fun <T : Comparable<T>> Matrix<T>.matrixMax(): T = this.mapNotNull { it.maxOrNull() }.maxOrNull()!!
fun <T : Comparable<T>> Matrix<T>.matrixMin(): T = this.mapNotNull { it.minOrNull() }.minOrNull()!!
fun <T> Matrix<T>.xRange() = this[0].indices
fun <T> Matrix<T>.yRange() = this.indices
fun <T> Matrix<T>.rotated(times: Int = 1): Matrix<T> = rotateMatrix(this, times)
fun <T> emptyMatrixOf(rows: Int, columns: Int, default: T) = MutableList(rows) { MutableList(columns) { default } }
fun <T> Matrix<T>.matrixCount(predicate: (T) -> Boolean) = this.sumOf { it.count(predicate) }
fun <T> Matrix<T>.matrixSumOf(selector: (T) -> Int) = this.sumOf { it.sumOf(selector) }
fun <T> Matrix<T>.getAdjacent(row: Int, col: Int): List<T> = this.getAdjacentCoordinates(row, col).map { it -> this[it.y][it.x] }

fun <T> Matrix<T>.getOrElse(p: Point, default: T): T {
	return try {
		this[p]
	} catch (_: IndexOutOfBoundsException) {
		default
	}
}

fun <T> Matrix<T>.getAdjacentCoordinates(row: Int, col: Int): List<Point> {
	val adjacent = mutableListOf<Point>()
	if (col != 0) adjacent.add(Point(col - 1, row))
	if (col != this.xRange().last) adjacent.add(Point(col + 1, row))
	if (row != 0) adjacent.add(Point(col, row - 1))
	if (row != this.yRange().last) adjacent.add(Point(col, row + indices.last))
	return adjacent
}

fun <T> Matrix<T>.getAdjacentCoordinates(point: Point): List<Point> = getAdjacentCoordinates(point.y, point.x)
fun <T> Matrix<T>.getRangesToEdge(point: Point) = getRangesToEdge(point.y, point.x)
fun <T> Matrix<T>.getRangesToEdge(row: Int, col: Int) = getColumnToEdge(row, col) + getRowToEdge(row, col)
fun <T> Matrix<T>.getColumnToEdge(row: Int, col: Int): List<List<T>> =
	this.getColumn(col).let { listOf(it.subList(0, row), it.subList(row + 1, it.size)) }

fun <T> Matrix<T>.getRowToEdge(row: Int, col: Int): List<List<T>> =
	this[row].let { listOf(it.subList(0, col), it.subList(col + 1, it.size)) }

fun <T> Matrix<T>.getSurroundingCoordinates(row: Int, col: Int): List<Point> {
	val adjacent = getAdjacentCoordinates(row, col).toMutableList()
	if (col != 0 && row != 0) adjacent.add(Point(col - 1, row - 1))
	if (col != 0 && row != this.yRange().last) adjacent.add(Point(col - 1, row + 1))
	if (col != this.xRange().last && row != 0) adjacent.add(Point(col + 1, row - 1))
	if (col != this.xRange().last && row != this.yRange().last) adjacent.add(Point(col + 1, row + 1))
	return adjacent
}

fun <T> Matrix<T>.getSurroundingCoordinates(point: Point): List<Point> =
	this.getSurroundingCoordinates(point.y, point.x)

/**
 * Returns a list containing only the non-null results of applying the given [transform] function to each element
 * and its index in the original collection.
 */
fun <T, R> Matrix<T>.mapMatrixIndexedNotNull(transform: (Point, T) -> R) = this.flatMapIndexed { y, row ->
	row.mapIndexedNotNull { x, it -> transform(Point(x, y), it) }
}

/*-----Helper Functions-----*/

private fun <T> rotateMatrix(matrix: Matrix<T>): Matrix<T> = List(matrix.xRange().last + 1) { i -> matrix.getColumn(i) }
private fun <T> rotateMatrix(matrix: Matrix<T>, times: Int): Matrix<T> {
	var newMatrix = matrix
	repeat(times) {
		newMatrix = rotateMatrix(newMatrix)
	}
	return newMatrix
}

fun <T> getCol(array: List<List<T>>, col: Int): List<T> {
	val rows = array.size
	val column = mutableListOf<T>()
	(0..<rows).forEach {
		try {
			column.add(array[it][col])
		} catch (_: IndexOutOfBoundsException) {
		}
	}
	return column
}

/*-----Math Functions-----*/

fun leastCommonMultiple(a: Long, b: Long): Long {
	val larger = if (a > b) a else b
	val maxLcm = a * b
	var lcm = larger
	while (lcm <= maxLcm) {
		if (lcm % a == 0L && lcm % b == 0L) {
			return lcm
		}
		lcm += larger
	}
	return maxLcm
}
