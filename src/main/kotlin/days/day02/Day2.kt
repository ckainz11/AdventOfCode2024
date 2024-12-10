package days.day02

import setup.Day
import util.allInts
import kotlin.math.abs

class Day2(override val input: String) : Day<Int>(input) {

	private val reports = input.lines().map { it.allInts() }
	private fun safe(report: List<Int>) = report.zipWithNext().all { (a, b) -> abs(a - b) in 1..3 } &&
			(report.sorted() == report || report.sorted().reversed() == report)

	override fun solve1(): Int = reports.count { safe(it) }

	override fun solve2(): Int = reports.count {
		safe(it) || (0..it.size).any { index ->
			safe(it.take(index) + it.drop(index + 1))
		}
	}
}
