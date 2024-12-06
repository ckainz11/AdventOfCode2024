package days.day06

import days.Day
import util.*

class Day6(override val input: String) : Day<Int>(input) {

	private val grid = input.lines().map { it.toCharArray().toList() }
	private val guardStart = grid.matrixIndexOfFirst { it == '^' }
	private val obstacles = grid.mapMatrixIndexedNotNull { point, c -> if (c == '#') point else null }

	override fun solve1(): Int = walk(grid) + 1

	private fun walk(grid: Matrix<Char>, partTwo: Boolean = false): Int {
		var dir = Point.UP
		var pos = guardStart
		val visited = mutableSetOf<Pair<Point, Point>>()
		while (grid.inBounds(pos + dir) && pos to dir !in visited) {
			visited += pos to dir
			while (grid[pos + dir] == '#')
				dir = dir.rotateClockwise()
			pos += dir
		}
		return if (!partTwo) visited.map { it.first }.toSet().size
		else if (pos to dir in visited) 1 else 0
	}

	override fun solve2(): Int = grid.mapMatrixIndexed { point, _ ->
		if (point == guardStart) 0
		else walk(replace(point, '#'), true)
	}.matrixSumOf { it }

	private fun replace(p: Point, c: Char): Matrix<Char> {
		val new = grid.toMutableMatrix()
		new[p] = c
		return new
	}
}
