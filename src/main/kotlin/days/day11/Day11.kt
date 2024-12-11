package days.day11

import setup.Day
import util.allLongs

class Day11(override val input: String) : Day<Long>(input) {

	private val stones = input.allLongs()
	private val blinkCache = mutableMapOf<Pair<Long, Int>, Long>()

	override fun solve1(): Long = blink(times = 25)
	override fun solve2(): Long = blink(times = 75)
	private fun blink(times: Int): Long = stones.sumOf { stone -> change(stone, times) }

	private fun change(stone: Long, times: Int): Long {
		return if (times == 0) 1
		else blinkCache.getOrPut(stone to times) {
			val s = stone.toString()
			if (stone == 0L) change(1, times - 1)
			else if (s.isEvenLength()) s.halved().sumOf { change(it.toLong(), times - 1) }
			else change(stone * 2024, times - 1)
		}
	}

	private fun String.halved() = chunked(length / 2)
	private fun String.isEvenLength() = length % 2 == 0

}
