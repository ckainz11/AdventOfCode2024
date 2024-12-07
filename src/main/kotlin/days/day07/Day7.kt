package days.day07

import days.Day
import util.allLongs

fun Long.concat(other: Long) = "$this$other".toLong()

class Day7(override val input: String) : Day<Long>(input) {

	override fun solve1(): Long = calculateCalibration(part = 1)
	override fun solve2(): Long = calculateCalibration(part = 2)

	private fun calculateCalibration(part: Int) = input.lines()
		.map { Equation.fromString(it, part) }
		.filter { it.solvable() }
		.sumOf { it.result }

	data class Equation(val result: Long, val numbers: List<Long>, val part: Int) {
		companion object {

			fun fromString(line: String, part: Int) = line.allLongs().let { Equation(it.first(), it.drop(1), part) }

			private val operations = listOf<(Long, Long) -> Long>(Long::plus, Long::times)
			private val part2Operations = operations + Long::concat

			fun operators(part: Int) = if (part == 1) operations else part2Operations
		}

		fun solvable(actual: Long = this.numbers.first(), numbers: List<Long> = this.numbers.drop(1)): Boolean {
			if (numbers.isEmpty()) return actual == result
			if (actual > result) return false

			return operators(part).any { solvable(it(actual, numbers.first()), numbers.drop(1)) }
		}

	}
}
