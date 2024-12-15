package util

import java.lang.IndexOutOfBoundsException

typealias Matrix<T> = List<List<T>>
typealias MutableMatrix<T> = MutableList<MutableList<T>>

/*----- instantiating a matrix -----*/

fun <T> matrixOf(vararg rows: List<T>): Matrix<T> = List(rows.size) { i -> rows[i] }
fun <T> Matrix<T>.toMutableMatrix(): MutableMatrix<T> = this.map { it.toMutableList() }.toMutableList()
fun <T> emptyMatrixOf(rows: Int, columns: Int, default: T) = List(rows) { List(columns) { default } }

fun <T> Matrix<T>.subMatrix(rows: Int, cols: Int, index: Point): Matrix<T> {
	val sub = mutableListOf<List<T>>()
	for (y in index.y..<index.y + rows) {
		sub.add(this[y].subList(index.x, index.x + cols))
	}
	return sub
}

/*----- Matrix Operations -----*/

operator fun <T> Matrix<T>.get(p: Point): T = this[p.y][p.x]
operator fun <T> MutableMatrix<T>.set(p: Point, value: T) {
	this[p.y][p.x] = value
}

fun <T> Matrix<T>.getOrElse(p: Point, default: T): T {
	return try {
		this[p]
	} catch (_: IndexOutOfBoundsException) {
		default
	}
}

/*----- Columns and Rows -----*/

fun <T> Matrix<T>.getColumn(col: Int): List<T> = this.map { it[col] }
fun <T> Matrix<T>.columns() = this[0].indices.map { this.getColumn(it) }
fun <T> Matrix<T>.xRange() = this[0].indices
fun <T> Matrix<T>.yRange() = this.indices
fun <T> Matrix<T>.width() = this[0].size
fun <T> Matrix<T>.height() = this.size

/*----- Stream Operations -----*/

fun <T, R> Matrix<T>.mapMatrix(transform: (T) -> R): Matrix<R> = this.map { it.map(transform) }

fun <T, R> Matrix<T>.mapMatrixIndexed(transform: (Point, T) -> R): Matrix<R> =
	this.mapIndexed { y, row -> row.mapIndexed { x, col -> transform(Point(x, y), col) } }

/**
 * Returns a list containing only the non-null results of applying the given [transform] function to each element
 * and its index in the original collection.
 */
fun <T, R> Matrix<T>.mapMatrixIndexedNotNull(transform: (Point, T) -> R) = this.flatMapIndexed { y, row ->
	row.mapIndexedNotNull { x, it -> transform(Point(x, y), it) }
}

fun <T> Matrix<T>.matrixForEachIndexed(action: (Point, T) -> Unit) {
	this.forEachIndexed { y, row -> row.forEachIndexed { x, col -> action(Point(x, y), col) } }
}

/**
 * Returns index of the first element matching the given [predicate], or a [Point] of (-1, -1) if the list does not contain such element.
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

fun <T : Comparable<T>> Matrix<T>.matrixMax(): T = this.mapNotNull { it.maxOrNull() }.maxOrNull()!!
fun <T : Comparable<T>> Matrix<T>.matrixMin(): T = this.mapNotNull { it.minOrNull() }.minOrNull()!!
fun <T> Matrix<T>.matrixCount(predicate: (T) -> Boolean) = this.sumOf { it.count(predicate) }
fun <T> Matrix<T>.matrixSumOf(selector: (T) -> Int) = this.sumOf { it.sumOf(selector) }

/*----- Point Integrations -----*/

/**
 * Returns a list of all the cardinal neighbors of [p] that are also in the [Matrix].
 * @param p the point to get the neighbors of
 */
fun <T> Matrix<T>.getCardinalNeighborsOf(p: Point): List<Point> = p.cardinalNeighbors().filter { it inBoundsOf this }

/*----- Rotating -----*/

fun <T> Matrix<T>.rotated(times: Int = 1): Matrix<T> = rotateMatrix(this, times)

private fun <T> rotateMatrix(matrix: Matrix<T>): Matrix<T> = matrix.xRange().map { x -> matrix.getColumn(x).reversed() }

private fun <T> rotateMatrix(matrix: Matrix<T>, times: Int): Matrix<T> {
	var newMatrix = matrix
	repeat(times) {
		newMatrix = rotateMatrix(newMatrix)
	}
	return newMatrix
}

/*-----String Representation-----*/

fun <T> Matrix<T>.matrixToString(seperator: String = ", "): String = this.joinToString("\n") { it.joinToString(seperator) }
