package days.day19

import setup.Day
import util.sections

class Day19(override val input: String) : Day<Long>(input) {

	private val availableTowels = input.sections()[0].split(", ").toSet()
	private val designs = input.sections()[1].lines()

	private val possibleCache = mutableMapOf<String, Boolean>()
	private val possibleDesigns = mutableListOf<String>()

	override fun solve1(): Long = designs
		.filter { it.isPossible() }
		.also { possibleDesigns.addAll(it) }
		.size.toLong()

	override fun solve2(): Long = possibleDesigns.sumOf { it.countPossibilities() }

	private fun String.isPossible(): Boolean =
		isEmpty() ||
		availableTowels
			.filter { startsWith(it) }
			.map { removePrefix(it) }
			.any { subDesign -> possibleCache.getOrPut(subDesign) { subDesign.isPossible() } }

	private val countCache = mutableMapOf<String, Long>()
	private fun String.countPossibilities(): Long =
		if (isEmpty()) 1
		else availableTowels
			.filter { startsWith(it) }
			.map { removePrefix(it) }
			.sumOf { subDesign -> countCache.getOrPut(subDesign) { subDesign.countPossibilities() } }
}
