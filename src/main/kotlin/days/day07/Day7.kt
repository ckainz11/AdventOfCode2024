package days.day07

import days.Day
import util.allLongs

class Day7(override val input: String) : Day<Long>(input) {

	private val equations = input.lines().map { line -> Equation.fromString(line) }

	override fun solve1(): Long = calculateCalibration(part = 1)
	override fun solve2(): Long = calculateCalibration(part = 2)

	private fun calculateCalibration(part: Int) = equations
		.filter { it.solvable(part) }
		.sumOf { it.result }

	data class Equation(val result: Long, val numbers: List<Long>) {
		companion object {

			fun fromString(line: String) = line.allLongs().let { Equation(it.first(), it.drop(1)) }

			private val operations = listOf(
				{ a: Long, b: Long -> a + b },
				{ a: Long, b: Long -> a * b },
			)

			private val part2Operations = operations + { a: Long, b: Long -> "$a$b".toLong() }
		}

		fun solvable(part: Int, actual: Long = this.numbers.first(), numbers: List<Long> = this.numbers.drop(1)): Boolean {
			if (numbers.isEmpty()) return actual == result
			if (actual > result) return false

			return if (part == 1) operations.any { solvable(part, it(actual, numbers.first()), numbers.drop(1)) }
			else part2Operations.any { solvable(part, it(actual, numbers.first()), numbers.drop(1)) }
		}

	}
}
