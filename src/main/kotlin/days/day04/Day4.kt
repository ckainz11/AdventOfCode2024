package days.day04

import days.Day
import util.*

class Day4(override val input: String) : Day<Int>(input) {

	private val matrix = input.lines().map { it.toList() }
	private val sam = listOf("MAS", "SAM")

	override fun solve1(): Int = matrix.mapMatrixIndexed { point, c ->
		if (c != 'X') 0
		else check(point)
	}.matrixSumOf { it }

	private fun check(p: Point): Int = Point.directions.fold(0) { acc, dir ->
		val letters = (1..3).map { matrix.getOrElse(p + (dir * it), '.') }.joinToString("")
		if (letters == "MAS") acc + 1
		else acc
	}

	override fun solve2(): Int {
		var count = 0
		for (y in 0..<matrix.size - 2) {
			for (x in 0..<matrix[y].size - 2) {
				val subMatrix = matrix.subMatrix(3, 3, Point(x, y))
				val (left, right) = getDiagonals(subMatrix)
				if (sam.any { it == left } && sam.any { it == right })
					count++
			}
		}
		return count
	}

	private fun getDiagonals(subMatrix: Matrix<Char>): Pair<String, String> {
		val topLeft = Point(0, 0)
		val topRight = Point(2, 0)
		fun getDiagonal(start: Point, direction: Point) = (1..3).map { subMatrix[start + (direction * it)] }.joinToString("")
		return getDiagonal(topLeft, Point.DOWN_RIGHT) to getDiagonal(topRight, Point.DOWN_LEFT)
	}
}
