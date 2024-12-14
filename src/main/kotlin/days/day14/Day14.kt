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
		.moveFor(seconds = 100)
		.getSafetyFactor()

	override fun solve2(): Int {
		var minSafetyToSeconds = Int.MAX_VALUE to -1
		var movableRobots = robots

		// Christmas tree is at the second with the lowest safety level
		// credit: https://www.reddit.com/r/adventofcode/comments/1he0g67/2024_day_14_part_2_the_clue_was_in_part_1/
		repeat(10000) { sec ->
			movableRobots = movableRobots.moveFor(seconds = 1)
			val safetyFactor = movableRobots.getSafetyFactor()
			if (safetyFactor < minSafetyToSeconds.first) {
				minSafetyToSeconds = safetyFactor to sec
			}
		}
		return minSafetyToSeconds.second + 1
	}

	private fun List<Robot>.normalizeVelocities(bounds: Point) = map { it.normalizeVelocity(bounds) }
	private fun List<Robot>.moveFor(seconds: Int) = map { it.moveForSeconds(seconds, bounds) }
	private fun List<Robot>.getSafetyFactor() = groupBy { getLocationOf(it.pos) }
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
}
