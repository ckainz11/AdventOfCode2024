package days.day21

import setup.Day
import util.*

typealias Key = Triple<String, Int, Int>

class Day21(override val input: String) : Day<Long>(input) {

	private val numericKeypad = listOf("789", "456", "123", " 0A").toKeypad()
	private val directionalKeypad = listOf(" ^A", "<v>").toKeypad()

	override fun solve1(): Long = solve(2)
	override fun solve2(): Long = solve(25)
	private fun solve(numOfRobots: Int) = input.lines().sumOf { code -> recurse(code, numOfRobots) * code.firstInt() }

	private var cache = mutableMapOf<Key, Long>()

	private fun recurse(sequence: String, limit: Int, depth: Int = 0): Long {
		val key = Triple(sequence, depth, limit)
		val keypad = if (depth == 0) numericKeypad else directionalKeypad


		return cache.getOrPut(key) {
			val start = keypad['A']!!

			fun String.isValidStartingAt(start: Point) = this.asSequence()
				.runningFold(start) { pos, dir -> pos + dir.toDirection() }.all { it in keypad.values }

			sequence.fold(start to 0L) { (pos, sum), char ->
				val next = keypad[char]!!
				val moves = (next - pos).asAtomicMoves()
				val paths = moves.permutations()
					.filter { path -> path.isValidStartingAt(pos) }
					.map { it + "A" }
					.ifEmpty { listOf("A") }
				val newSum = sum + if (depth == limit) paths.minOf { it.length }.toLong() else paths.minOfOrNull { recurse(it, limit, depth + 1) }!!
				next to newSum
			}.second
		}
	}

	private fun Point.asAtomicMoves() = ((if (y < 0) "^".repeat(-y) else "v".repeat(y)) + if (x < 0) "<".repeat(-x) else ">".repeat(x))

	private fun List<String>.toKeypad() = this.map { it.toList() }
		.mapMatrixIndexedNotNull { point, c -> if (c != ' ') c to point else null } // filter out empty space
		.associate { it }

	private fun Char.toDirection() = when (this) {
		'^' -> Point.UP
		'v' -> Point.DOWN
		'<' -> Point.LEFT
		'>' -> Point.RIGHT
		else -> throw IllegalArgumentException("Invalid direction: $this")
	}
}
