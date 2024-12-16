package days.day16

import setup.Day
import util.Point
import util.asMatrix
import util.get
import util.matrixIndexOfFirst
import java.util.PriorityQueue

class Day16(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix()
	private val start = grid.matrixIndexOfFirst { it == 'S' }
	private val end = grid.matrixIndexOfFirst { it == 'E' }
	private val direction = Point.RIGHT

	override fun solve1(): Int {
		val scores = mutableMapOf(start to direction to 0)

		val toVisit = PriorityQueue<Reindeer>().apply { add(Reindeer(start, direction, 0)) }

		while (toVisit.isNotEmpty()) {
			val (location, direction, score) = toVisit.poll()

			if (location == end) return score

			if (location to direction in scores && scores[location to direction]!! < score) continue

			scores[location to direction] = score

			if (!grid[location].isWall())
				toVisit.add(Reindeer(location + direction, direction, score + 1))

			toVisit.add(Reindeer(location, direction.rotateClockwise(), score + 1000))
			toVisit.add(Reindeer(location, direction.rotateCounterClockwise(), score + 1000))
		}
		return 0
	}

	override fun solve2(): Int {
		val scores = mutableMapOf(start to direction to 0)
		val toVisit = PriorityQueue<Triple<List<Point>, Point, Int>>(compareBy { it.third })
		toVisit.add(Triple(listOf(start), direction, 0))

		var min = Int.MAX_VALUE
		val bestPath = mutableSetOf<Point>()

		while (toVisit.isNotEmpty()) {
			val (path, direction, score) = toVisit.poll()

			if (path.last() == end) {
				if (score <= min) min = score
				else return bestPath.size
				bestPath.addAll(path)
			}

			if (path.last() to direction in scores && scores[path.last() to direction]!! < score) continue

			scores[path.last() to direction] = score

			if (!grid[path.last()].isWall()) {
				val next = path.last() + direction
				toVisit.add(Triple(path + next, direction, score + 1))
			}
			toVisit.add(Triple(path, direction.rotateClockwise(), score + 1000))
			toVisit.add(Triple(path, direction.rotateCounterClockwise(), score + 1000))
		}

		return 0
	}

	data class Reindeer(val location: Point, val direction: Point, val score: Int) : Comparable<Reindeer> {

		override fun compareTo(other: Reindeer): Int {
			return score.compareTo(other.score)
		}
	}

	private fun Char.isWall(): Boolean = this == '#'
}
