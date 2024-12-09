package days.day06

import days.Day
import util.*

class Day6(override val input: String) : Day<Int>(input) {

	private val grid = input.lines().map { it.toCharArray().toList() }
	private val guardStart = grid.matrixIndexOfFirst { it == '^' }
	private val path = getPath()

	override fun solve1(): Int = path.size

	override fun solve2(): Int = path.sumOf { p ->
		if (p == guardStart) 0
		else walk(putObstacle(p))
	}

	private fun walk(grid: Matrix<Char>): Int {
		var dir = Point.UP
		var pos = guardStart
		val visited = mutableSetOf<Pair<Point, Point>>()
		while (pos + dir inBoundsOf grid && pos to dir !in visited) {
			visited += pos to dir
			while (grid[pos + dir] == '#')
				dir = dir.rotateClockwise()
			pos += dir
		}
		return if (pos to dir in visited) 1 else 0
	}

	private fun getPath(): Set<Point> {
		var dir = Point.UP
		var pos = guardStart
		val path = mutableSetOf<Point>()
		while (pos + dir inBoundsOf grid) {
			path += pos
			while (grid[pos + dir] == '#')
				dir = dir.rotateClockwise()
			pos += dir
		}
		path += pos
		return path
	}

	private fun putObstacle(p: Point): Matrix<Char> {
		val new = grid.toMutableMatrix()
		new[p] = '#'
		return new
	}
}
