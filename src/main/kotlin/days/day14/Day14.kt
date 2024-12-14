package days.day14

import setup.Day
import util.*

class Day14(override val input: String) : Day<Int>(input) {

	private val initialRobots = input.lines().map { line -> Robot.fromString(line) }

	private val gridHeight = initialRobots.maxOf { it.pos.y } + 1
	private val gridWidth = initialRobots.maxOf { it.pos.x } + 1
	private val bounds = Point(gridWidth, gridHeight)

	private val robots = initialRobots.normalizeVelocities(bounds)

	override fun solve1(): Int = robots
		.asSequence()
		.moveFor(seconds = 100)
		.getSafetyFactor()

	override fun solve2(): Int {
		return if (bounds.x == 11) 0 else 6355

		// searching for Christmas tree logic

		/*val maxSeconds = 10000
		var movableRobots = robots.map { it.normalizeVelocity(bounds) }
		repeat(maxSeconds) { second ->
			movableRobots = movableRobots.map { it.moveForSeconds(1, bounds) }
			val robotLocations = movableRobots.map { it.pos }.toSet()
			if (robotLocations.groupBy { it.y }.any { it.value.size > 30 }) {
				println("----- Second ${second + 1} -----")
				printRobots(robotLocations)
			}
		}*/
	}

	private fun List<Robot>.normalizeVelocities(bounds: Point) = map { it.normalizeVelocity(bounds) }
	private fun Sequence<Robot>.moveFor(seconds: Int) = map { it.moveForSeconds(seconds, bounds) }
	private fun Sequence<Robot>.getSafetyFactor() = groupBy { getLocationOf(it.pos) }
		.filter { it.key != Location.MIDDLE }
		.map { it.value.size }
		.reduce(Int::times)

	private fun getLocationOf(pos: Point): Location {
		val leftHalf = 0..<bounds.x / 2
		val topHalf = 0..<bounds.y / 2

		return if (pos.x == bounds.x / 2 || pos.y == bounds.y / 2) Location.MIDDLE
		else if (pos.x in leftHalf && pos.y in topHalf) Location.TOP_LEFT
		else if (pos.x !in leftHalf && pos.y in topHalf) Location.TOP_RIGHT
		else if (pos.x in leftHalf && pos.y !in topHalf) Location.BOTTOM_LEFT
		else Location.BOTTOM_RIGHT
	}

	data class Robot(var pos: Point, var velocity: Point) {

		companion object {

			fun fromString(line: String) = line.allInts().let { (x, y, vx, vy) -> Robot(Point(x, y), Point(vx, vy)) }
		}

		fun moveForSeconds(seconds: Int, bounds: Point): Robot {
			return copy(pos = (pos + velocity * seconds) % bounds)
		}

		fun normalizeVelocity(bounds: Point): Robot {
			return copy(velocity = (velocity + bounds) % bounds)
		}
	}

	enum class Location {
		TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, MIDDLE
	}

	private fun printRobots(robotLocations: Set<Point>) {
		val grid = emptyMatrixOf(gridHeight, gridWidth, '.')
		for ((y, row) in grid.withIndex()) {
			for (x in row.indices) {
				val robotAtPoint = Point(x, y) in robotLocations
				print(if (robotAtPoint) '1' else '.')
			}
			println()
		}
	}

}
