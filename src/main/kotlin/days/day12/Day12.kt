package days.day12

import setup.Day
import util.*

class Day12(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix()
	private val coords = grid.mapMatrixIndexedNotNull { p, _ -> p }
	private val regions = getRegions()

	override fun solve1(): Int = getTotalPrice(part = 1)
	override fun solve2(): Int = getTotalPrice(part = 2)
	private fun getTotalPrice(part: Int) = regions.sumOf { it.getPrice(part) }

	data class Region(val coords: Set<Point>) {

		fun getPrice(part: Int) = area * if (part == 1) perimeter else numOfSides

		private val area = coords.size
		private val perimeter = coords.sumOf { p -> p.cardinalNeighbors().count { it !in coords } }

		private val numOfSides = coords.sumOf { point ->
			(Point.cardinals + Point.cardinals.first()).zipWithNext().count { (d1, d2) ->
				val a = point + d1
				val b = point + d2
				val c = point + d1 + d2
				(a !in coords && b !in coords) || (a in coords && b in coords && c !in coords)
			}
		}
	}

	private fun getRegions() = buildList {
		val visited = mutableSetOf<Point>()
		for (p in coords) {
			if (p in visited) continue
			val queue = ArrayDeque<Point>().apply { add(p) }
			val regionCoords = mutableSetOf(p)
			while (queue.isNotEmpty()) {
				val current = queue.removeFirst()
				if (!visited.add(current)) continue
				regionCoords.add(current)
				val neighbors = grid.getCardinalNeighborsOf(current).filter { it in coords && grid[p] == grid[it] }
				queue.addAll(neighbors)
			}
			add(Region(regionCoords))
		}
	}
}
