package util

import java.lang.IllegalArgumentException
import kotlin.math.abs

data class Point(var x: Int, var y: Int) {

	operator fun plus(other: Point) = Point(other.x + x, other.y + y)
	operator fun minus(other: Point) = Point(x - other.x, y - other.y)
	operator fun times(n: Int) = Point(x * n, y * n)
	operator fun rem(other: Point) = Point(x % other.x, y % other.y)

	infix fun inBoundsOf(matrix: Matrix<*>) = y in matrix.indices && x in matrix[0].indices

	fun mDist(other: Point): Int = abs(this.x - other.x) + abs(this.y - other.y)

	/**
	 * Rotates the point 90 degrees clockwise around the origin (0, 0).
	 */
	fun rotateClockwise(): Point {
		return when (this) {
			UP * y -> RIGHT * y
			RIGHT * x -> DOWN * x
			DOWN * y -> LEFT * y
			LEFT * x -> UP * x
			else -> throw IllegalArgumentException("Point $this is not a cardinal direction")
		}
	}

	fun rotateCounterClockwise(): Point {
		return when (this) {
			UP * y -> LEFT * y
			LEFT * x -> DOWN * x
			DOWN * y -> RIGHT * y
			RIGHT * x -> UP * x
			else -> throw IllegalArgumentException("Point $this is not a cardinal direction")
		}
	}

	fun cardinalNeighbors() = cardinals.map { this + it }

	companion object {

		val LEFT = Point(-1, 0)
		val RIGHT = Point(1, 0)
		val UP = Point(0, -1)
		val DOWN = Point(0, 1)
		val DOWN_RIGHT = DOWN + RIGHT
		val DOWN_LEFT = DOWN + LEFT
		val UP_LEFT = UP + LEFT
		val UP_RIGHT = UP + RIGHT

		val cardinals = listOf(RIGHT, DOWN, LEFT, UP)
		val diagonals = listOf(DOWN_RIGHT, DOWN_LEFT, UP_LEFT, UP_RIGHT)
		val directions = cardinals + diagonals
	}
}
