package days.day04

import setup.Day
import util.*

class Day4(override val input: String) : Day<Int>(input) {

	private val matrix = input.lines().map { it.toList() }
	private val diagonals = listOf("MAS", "SAM")

	override fun solve1(): Int = matrix.mapMatrixIndexed { point, c ->
		if (c != 'X') 0
		else checkDirections(point)
	}.matrixSumOf { it }

	override fun solve2(): Int {
		var count = 0
		for (y in 0..<matrix.size - 2) {
			for (x in 0..<matrix[y].size - 2) {
				val subMatrix = matrix.subMatrix(3, 3, Point(x, y))
				val (left, right) = getDiagonals(subMatrix)
				if (diagonals.any { it == left } && diagonals.any { it == right })
					count++
			}
		}
		return count
	}

	private fun checkDirections(p: Point): Int = Point.directions.fold(0) { acc, dir ->
		val letters = getLine(p, dir, 1..3, matrix)
		if (letters == "MAS") acc + 1
		else acc
	}

	private fun getDiagonals(subMatrix: Matrix<Char>): Pair<String, String> {
		val topLeft = Point(0, 0)
		val topRight = Point(2, 0)
		fun getDiagonal(start: Point, direction: Point) = getLine(start, direction, 0..2, subMatrix)
		return getDiagonal(topLeft, Point.DOWN_RIGHT) to getDiagonal(topRight, Point.DOWN_LEFT)
	}

	private fun getLine(start: Point, dir: Point, range: IntRange, matrix: Matrix<Char>) = range
		.map { matrix.getOrElse(start + (dir * it), '.') }
		.joinToString("")

}
