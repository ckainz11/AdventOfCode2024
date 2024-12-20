package days.day20

import setup.Day
import util.Point
import util.asMatrix
import util.get
import util.matrixIndexOfFirst

const val MIN_SECONDS_SAVED = 100

class Day20(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix()
	private val start = grid.matrixIndexOfFirst { it == 'S' }
	private val end = grid.matrixIndexOfFirst { it == 'E' }

	private val path = getRaceTrack()

	override fun solve1(): Int = path.indices.sumOf { it.cheatFor(2) }
	override fun solve2(): Int = path.indices.sumOf { it.cheatFor(20) }

	private fun Int.cheatFor(seconds: Int): Int {
		val cheatStart = path[this]
		return (this + MIN_SECONDS_SAVED + 2..<path.size).count { endIndex ->
			val cheatEnd = path[endIndex]
			cheatStart.mDist(cheatEnd) <= seconds &&
			endIndex - this - cheatStart.mDist(cheatEnd) >= MIN_SECONDS_SAVED
		}
	}

	private fun getRaceTrack(): List<Point> {
		val path = mutableSetOf(start)
		var current = start
		while (current != end) {
			val next = current.cardinalNeighbors()
				.first { !it.isWall() && it !in path }

			path.add(next)
			current = next
		}
		return path.toList()
	}

	private fun Point.isWall() = grid[this] == '#'
}
