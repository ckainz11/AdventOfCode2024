package days.day03

import days.Day
import util.allInts

class Day3(override val input: String) : Day<Int>(input) {

	private val multiRegex = "mul\\(\\d+,\\d+\\)".toRegex()
	private val doRegex = "(don't\\(\\)|do\\(\\))".toRegex()

	private val multiplications = multiRegex.findAll(input).map { match -> Multi.from(match) }
	private val instructions = doRegex.findAll(input).map { match -> Instruction.from(match) }

	override fun solve1(): Int = multiplications.sumOf { it.result }
	override fun solve2(): Int = multiplications.sumOf { if (it.canRunWith(instructions)) it.result else 0 }

	data class Instruction(val start: Int, val doo: Boolean) {

		companion object {

			fun from(match: MatchResult) = Instruction(match.range.first, match.value == "do()")

		}

		fun contains(multi: Multi) = multi.start > start

	}

	data class Multi(val start: Int, val result: Int) {

		fun canRunWith(instructions: Sequence<Instruction>) = instructions.findLast { it.contains(this) }?.doo ?: true // first is always "do"

		companion object {

			fun from(match: MatchResult) = Multi(match.range.first, match.value.allInts().let { (a, b) -> a * b })

		}
	}
}
