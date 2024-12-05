package days.day05

import days.Day
import util.allInts
import util.middle
import util.pairwise
import util.sections

class Day5(override val input: String) : Day<Int>(input) {

	private val sections = input.sections()
	private val rules = sections[0].lines().map { it.allInts().let { (a, b) -> a to b } }
	private val updates = sections[1].lines().map { it.allInts() }

	override fun solve1(): Int = updates
		.filter { it.isValid() }
		.sumOf { it.middle() }

	override fun solve2() = updates
		.filter { !it.isValid() }
		.map { it.sorted() }
		.sumOf { it.middle() }

	private fun List<Int>.isValid() = pairwise().all { it in rules }
	private fun List<Int>.sorted() = map { Page(it, rules) }.sorted().map { it.value }

	data class Page(val value: Int, val rules: List<Pair<Int, Int>>) : Comparable<Page> {

		override fun compareTo(other: Page): Int {
			if (value == other.value) return 0
			return if (value to other.value in rules) -1 else 1
		}

	}

}
