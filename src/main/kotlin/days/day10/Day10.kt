package days.day10

import days.Day
import util.*

class Day10(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix().mapMatrix { it.digitToInt() }
	private val trailheads = grid.mapMatrixIndexedNotNull { p, c -> if (c == 0) p else null }

	override fun solve1(): Int = trailheads.sumOf { it.calcScore() }
	override fun solve2(): Int = trailheads.sumOf { it.calcRating() }

	private fun Point.calcScore() = calc(this).distinct().size
	private fun Point.calcRating() = calc(this).size

	private fun calc(trailhead: Point): List<Point> {
		if (grid[trailhead] == 9) return listOf(trailhead)
		val neighbors = trailhead.cardinalNeighbors()
			.filter { it inBoundsOf grid && grid[it] == grid[trailhead] + 1 }
		if (neighbors.isEmpty()) return emptyList()
		return neighbors.flatMap { calc(it) }
	}
}
