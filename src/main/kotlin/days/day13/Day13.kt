package days.day13

import setup.Day
import util.*

typealias LongPoint = Pair<Long, Long>

fun LongPoint.x() = first
fun LongPoint.y() = second

class Day13(override val input: String) : Day<Long>(input) {

	private val clawMachines = input.sections()
		.map { section ->
			val (a, b, prize) = section.lines()
			val buttonA = a.allInts().let { (x, y) -> LongPoint(x.toLong(), y.toLong()) }
			val buttonB = b.allInts().let { (x, y) -> LongPoint(x.toLong(), y.toLong()) }
			prize.allInts().let { (x, y) -> ClawMachine(buttonA, buttonB, Point(x, y)) }
		}

	private val adjustmentPart1 = 0L to 0L
	override fun solve1(): Long = clawMachines.sumOf { it.solveEquation(adjustmentPart1) }

	private val adjustmentPart2 = 10_000_000_000_000L to 10_000_000_000_000L
	override fun solve2(): Long = clawMachines.sumOf { it.solveEquation(adjustmentPart2) }

	data class ClawMachine(val buttonA: LongPoint, val buttonB: LongPoint, val prizeLocation: Point) {

		private val buttonATokenCost = 3L
		private val buttonBTokenCost = 1L

		fun solveEquation(adjustment: LongPoint): Long {
			val det = buttonA.x() * buttonB.y() - buttonB.x() * buttonA.y()
			if (det == 0L) return 0L

			val adjustedPrizeX = prizeLocation.x + adjustment.first
			val adjustedPrizeY = prizeLocation.y + adjustment.second
			val abDet = Pair(
				(adjustedPrizeX * buttonB.y()) - (adjustedPrizeY * buttonB.x()),
				(buttonA.x() * adjustedPrizeY) - (buttonA.y() * adjustedPrizeX)
			)

			val detX = abDet.first % det
			val detY = abDet.second % det

			if (detX != 0L || detY != 0L) return 0L

			return (abDet.first / det) * buttonATokenCost + (abDet.second / det) * buttonBTokenCost
		}
	}
}
