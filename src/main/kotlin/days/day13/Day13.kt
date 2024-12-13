package days.day13

import setup.Day
import util.*
import java.math.BigInteger

typealias BigPoint = Pair<BigInteger, BigInteger>

fun BigPoint.x() = first
fun BigPoint.y() = second

class Day13(override val input: String) : Day<BigInteger>(input) {

	private val clawMachines = input.sections()
		.map { section ->
			val (a, b, prize) = section.lines()
			val buttonA = a.allInts().let { (x, y) -> BigPoint(x.toBigInteger(), y.toBigInteger()) }
			val buttonB = b.allInts().let { (x, y) -> BigPoint(x.toBigInteger(), y.toBigInteger()) }
			prize.allInts().let { (x, y) -> ClawMachine(buttonA, buttonB, Point(x, y)) }
		}

	private val adjustmentPart1 = BigInteger.ZERO to BigInteger.ZERO
	override fun solve1(): BigInteger = clawMachines.sumOf { it.solveEquation(adjustmentPart1) }

	private val adjustmentPart2 = BigInteger("10000000000000") to BigInteger("10000000000000")
	override fun solve2(): BigInteger = clawMachines.sumOf { it.solveEquation(adjustmentPart2) }

	data class ClawMachine(val buttonA: BigPoint, val buttonB: BigPoint, val prizeLocation: Point) {

		private val buttonATokenCost = 3.toBigInteger()
		private val buttonBTokenCost = 1.toBigInteger()

		fun solveEquation(adjustment: BigPoint): BigInteger {
			val det = buttonA.x() * buttonB.y() - buttonB.x() * buttonA.y()
			if (det == BigInteger.ZERO) return BigInteger.ZERO

			val adjustedPrizeX = prizeLocation.x.toBigInteger() + adjustment.first
			val adjustedPrizeY = prizeLocation.y.toBigInteger() + adjustment.second
			val abDet = Pair(
				(adjustedPrizeX * buttonB.y()) - (adjustedPrizeY * buttonB.x()),
				(buttonA.x() * adjustedPrizeY) - (buttonA.y() * adjustedPrizeX)
			)

			val detX = abDet.first % det
			val detY = abDet.second % det

			if (detX != BigInteger.ZERO || detY != BigInteger.ZERO) return BigInteger.ZERO

			return (abDet.first / det) * buttonATokenCost + (abDet.second / det) * buttonBTokenCost
		}
	}
}
