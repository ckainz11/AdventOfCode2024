package days.day10

import days.Day
import util.*

class Day10(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix().mapMatrix { it.digitToInt() }
	private val trailheads = grid.mapMatrixIndexedNotNull { p, c -> if (c == 0) p else null }

	override fun solve1(): Int = trailheads.sumOf { score(it).size }
	override fun solve2(): Int = trailheads.sumOf { score2(it) }

	private fun score(trailhead: Point): Set<Point> {
		if (grid[trailhead] == 9) return setOf(trailhead)
		val visitedEnds = mutableSetOf<Point>()
		val neighbors = trailhead.cardinalNeighbors()
		for (neighbor in neighbors) {
			if (neighbor inBoundsOf grid && grid[neighbor] == grid[trailhead] + 1)
				visitedEnds += score(neighbor)
		}
		return visitedEnds
	}

	private fun score2(trailhead: Point): Int {
		if (grid[trailhead] == 9) return 1
		var score = 0
		val neighbors = trailhead.cardinalNeighbors()
		for (neighbor in neighbors) {
			if (neighbor inBoundsOf grid && grid[neighbor] == grid[trailhead] + 1)
				score += score2(neighbor)
		}
		return score
	}
}
