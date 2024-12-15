package days.day15

import setup.Day
import util.*

class Day15(override val input: String) : Day<Int>(input) {

	private val sections = input.sections()

	private val grid = sections[0].asMatrix()
	private val instructions = sections[1].lines().flatMap { line -> line.map { c -> c.toPointDirection() } }

	private val robotStart = grid.matrixIndexOfFirst { it == '@' }

	private val initialBoxes = grid.mapMatrixIndexedNotNull { point, c -> if (c == 'O') point else null }.toMutableSet()
	private val boxes = initialBoxes.toMutableSet()
	private val enlargedBoxes = enlargeBoxes(initialBoxes)

	private val walls = grid.mapMatrixIndexedNotNull { point, c -> if (c == '#') point else null }.toSet()
	private val enlargedWalls = enlargeWalls(walls)

	override fun solve1(): Int {
		var curr = robotStart

		for (instruction in instructions) {
			val next = curr + instruction
			if (next in walls) continue
			else if (next in boxes) pushBoxes(curr, instruction).also { success -> if (success) curr = next }
			else curr = next
		}

		return boxes.sumOf { it.x + (it.y * 100) }
	}

	private fun pushBoxes(robot: Point, direction: Point): Boolean {
		val boxesToPush = getBoxesInDirection(robot, direction)

		if (!boxesToPush.canPushIn(direction))
			return false

		boxes.removeAll(boxesToPush)
		boxesToPush.map { it + direction }.forEach { boxes.add(it) }
		return true
	}

	private fun Set<Point>.canPushIn(direction: Point): Boolean = all { it + direction !in walls }

	private fun getBoxesInDirection(robot: Point, direction: Point): Set<Point> {
		val next = robot + direction
		return if (next in boxes) getBoxesInDirection(next, direction) + next
		else emptySet()
	}

	override fun solve2(): Int {
		var curr = Point(robotStart.x * 2, robotStart.y)

		for (instruction in instructions) {
			val next = curr + instruction
			if (next in enlargedWalls) continue
			if (enlargedBoxes.boxAt(next)) pushLargeBoxes(curr, instruction).also { success -> if (success) curr = next }
			else curr = next
		}

		return enlargedBoxes.sumOf { it.score }
	}

	private fun pushLargeBoxes(curr: Point, instruction: Point): Boolean {
		val next = curr + instruction
		val firstBox = enlargedBoxes.first { it.left == next || it.right == next }
		val connectedBoxes = firstBox.getConnectedBoxesInDirection(instruction) + firstBox

		if (!connectedBoxes.pushableInDirection(instruction))
			return false

		enlargedBoxes.removeAll(connectedBoxes)
		connectedBoxes.forEach { enlargedBoxes.add(LargeBox(it.left + instruction, it.right + instruction)) }
		return true

	}

	data class LargeBox(val left: Point, val right: Point) {

		val score = left.x + (left.y * 100)

		operator fun plus(direction: Point) = buildSet {
			when (direction) {
				Point.UP, Point.DOWN -> {
					add(LargeBox(left + direction, right + direction)) // directly above or below
					add(LargeBox(left + Point.LEFT + direction, left + direction)) // overlapping on left
					add(LargeBox(right + direction, right + Point.RIGHT + direction)) // overlapping on right
				}

				Point.LEFT -> add(LargeBox(left + (direction * 2), left + direction))
				Point.RIGHT -> add(LargeBox(right + direction, right + (direction * 2)))
				else -> error("illegal direction: $direction")
			}
		}
	}

	private fun Set<LargeBox>.boxAt(point: Point) =
		contains(LargeBox(point, point + Point.RIGHT)) ||
		contains(LargeBox(point + Point.LEFT, point))

	private fun LargeBox.getConnectedBoxesInDirection(direction: Point): Set<LargeBox> {
		val nextBoxes = (this + direction).filter { it in enlargedBoxes }
		return if (nextBoxes.isEmpty()) emptySet()
		else nextBoxes.flatMap { it.getConnectedBoxesInDirection(direction) }.toSet() + nextBoxes
	}

	private fun Set<LargeBox>.pushableInDirection(direction: Point) = all { box ->
		box.left + direction !in enlargedWalls &&
		box.right + direction !in enlargedWalls
	}

	private fun enlargeWalls(oldWalls: Set<Point>) = buildSet {
		for (p in oldWalls) {
			val left = Point(p.x * 2, p.y)
			val right = Point(p.x * 2 + 1, p.y)
			add(left)
			add(right)
		}
	}.toMutableSet()

	private fun enlargeBoxes(oldBoxes: Set<Point>) = buildSet {
		for (p in oldBoxes) {
			val left = Point(p.x * 2, p.y)
			val right = Point(p.x * 2 + 1, p.y)
			add(LargeBox(left, right))
		}
	}.toMutableSet()

	private fun Char.toPointDirection(): Point {
		return when (this) {
			'^' -> Point.UP
			'>' -> Point.RIGHT
			'v' -> Point.DOWN
			'<' -> Point.LEFT
			else -> throw IllegalArgumentException("Invalid direction: $this")
		}
	}

}
