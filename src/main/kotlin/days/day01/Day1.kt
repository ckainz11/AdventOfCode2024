package days.day01

import setup.Day
import kotlin.math.abs

class Day1(override val input: String) : Day<Int>(input) {

	private val cols = input.lines()
		.map { it.split("   ") }
		.map { it[0].toInt() to it[1].toInt() }
		.unzip()

	override fun solve1(): Int = cols.first.sorted()
		.zip(cols.second.sorted())
		.sumOf { (l, r) -> abs(r - l) }

	override fun solve2(): Int = cols.first.sumOf { l -> l * cols.second.count { r -> l == r } }
}
