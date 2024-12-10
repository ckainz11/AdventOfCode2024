package days.day10

import setup.Day
import util.*

class Day10(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix().mapMatrix { it.digitToInt() }
	private val trailheads = grid.mapMatrixIndexedNotNull { p, c -> if (c == 0) p else null }

	override fun solve1(): Int = trailheads.sumOf { it.calcScore() }
	override fun solve2(): Int = trailheads.sumOf { it.calcRating() }

	private fun Point.calcScore() = calc(this).distinct().size
	private fun Point.calcRating() = calc(this).size

	private fun calc(current: Point): List<Point> {
		if (grid[current] == 9) return listOf(current)
		val nextSteps = current.cardinalNeighbors()
			.filter { next -> next inBoundsOf grid && grid[next] == grid[current] + 1 }
		if (nextSteps.isEmpty()) return emptyList()
		return nextSteps.flatMap { calc(it) }
	}
}
