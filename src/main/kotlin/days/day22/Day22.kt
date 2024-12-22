package days.day22

import setup.Day

const val CONSTANT = 16777216L

class Day22(override val input: String) : Day<Long>(input) {

	private val initialSecrets = input.lines().map { it.toLong() }

	override fun solve1(): Long = initialSecrets.sumOf { it.generateSecrets(2000).last() }

	override fun solve2(): Long = initialSecrets.flatMap { secret ->
		(listOf(secret) + secret.generateSecrets(2000))
			.map { it.onesDigit() }
			.zipWithNext { a, b -> b - a to b }
			.windowed(4) { it.map { (change, _) -> change } to it.last().second }
			.distinctBy { it.first }
	}.groupBy({ it.first }, { it.second })
		.mapValues { it.value.sum() }
		.values.max()

	private fun Long.generateSecrets(times: Int): List<Long> {
		val secrets = mutableListOf<Long>()
		var secret = this
		repeat(times) {
			secret = secret.nextSecret()
			secrets.add(secret)
		}
		return secrets
	}

	private fun Long.nextSecret(): Long {
		val one = ((this * 64) xor this) % CONSTANT
		val two = ((one / 32) xor one) % CONSTANT
		val three = ((two * 2048) xor two) % CONSTANT
		return three
	}

	private fun Long.onesDigit() = this % 10
}
