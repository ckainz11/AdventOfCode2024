package days.day03

import days.Day
import util.allInts

class Day3(override val input: String) : Day<Int>(input) {

	private val multiRegex = "mul\\(\\d+,\\d+\\)".toRegex()
	private val dontRegex = "(don't|do)".toRegex()

	private val multis = multiRegex.findAll(input).map { Multi(it.range.first, it.value.allInts().let { (a, b) -> a * b }) }
	private val doRanges = listOf(
		DoRange(0..0, true),
		*(dontRegex.findAll(input).map { DoRange(it.range, it.value == "do") }.toList().toTypedArray()),
		DoRange(input.length..input.length, true)
	).zipWithNext { a, b -> DoRange(a.range.last..b.range.first, a.doo) }.filter { it.doo }

	override fun solve1(): Int = multis.sumOf { it.multi }
	override fun solve2(): Int = multis.sumOf { if (doRanges.any { range -> range.contains(it) }) it.multi else 0 }

	data class DoRange(val range: IntRange, val doo: Boolean) {

		fun contains(multi: Multi) = multi.start in range
	}

	data class Multi(val start: Int, val multi: Int)
}
