package days.day08

import days.Day
import util.*

class Day8(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix()
	private val frequencies = grid
		.mapMatrixIndexedNotNull { point, c -> if (c == '.') null else c to point }
		.groupBy { it.first }
		.mapValues { it.value.map { pair -> pair.second } }
		.values.map { antennas -> Frequency(antennas, grid) }

	override fun solve1(): Int = solve(part = 1)
	override fun solve2(): Int = solve(part = 2)

	private fun solve(part: Int) = frequencies.flatMap { it.getAntinodes(part) }.distinct().size

	data class Frequency(val antennas: List<Point>, private val grid: Matrix<Char>) {

		fun getAntinodes(part: Int) = antennas.pairwise().flatMap { (a, b) ->
			val dist = b - a
			if (a.mDist(b) == 1) emptySet()
			else if (part == 1) projectOnce(a, b, dist)
			else projectLine(a, b, dist)
		}

		private fun projectOnce(a: Point, b: Point, dist: Point) =
			listOf(b + dist, a - dist).filter { it inBoundsOf grid }

		private fun projectLine(a: Point, b: Point, dist: Point) =
			projectFromAntenna(b, dist) + projectFromAntenna(a, dist * -1)

		private fun projectFromAntenna(antenna: Point, dir: Point) =
			generateSequence(antenna) { it + dir }
				.takeWhile { it inBoundsOf grid }
				.toList()

	}
}
