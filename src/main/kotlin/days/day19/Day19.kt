package days.day19

import setup.Day
import util.sections

class Day19(override val input: String) : Day<Long>(input) {

	private val towels = input.sections()[0].split(", ")
	private val designs = input.sections()[1].lines()

	override fun solve1(): Long = designs.count { it.countPossibilities() > 0 }.toLong()
	override fun solve2(): Long = designs.sumOf { it.countPossibilities() }

	private val cache = mutableMapOf("" to 1L)
	private fun String.countPossibilities(): Long = cache.getOrPut(this) {
		towels
			.filter { startsWith(it) }
			.sumOf { removePrefix(it).countPossibilities() }
	}
}
