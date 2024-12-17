package days.day17

import setup.Day
import util.allInts
import util.firstInt
import util.sections
import kotlin.math.pow

class Day17(override val input: String) : Day<String>(input) {

	private fun parseInput(input: String): Pair<Triple<Int, Int, Int>, List<Int>> {
		val (register, instructions) = input.sections()
		val values = register.allInts()
		val inst = instructions.allInts().map { it.toString().single().digitToInt(radix = 8) }
		return Triple(values[0], values[1], values[2]) to inst
	}

	override fun solve1(): String {
		val (register, instructions) = input.sections()
		val (a, b, c) = register.lines().map { it.firstInt().toLong() }
		val computer = Computer.withRegister(a, b, c)
		return computer.run(instructions.allInts()).joinToString(",")
	}

	override fun solve2(): String {
		val (register, instructions) = parseInput(input)

		fun findCandidateBits(regA: Long, output: Int): List<Long> = buildList {
			for (bits in 0b000L..0b111L) {
				val candidate = (regA shl 3) or bits
				val isMatch = Computer.withRegister(candidate, register.second.toLong(), register.third.toLong()).run(instructions).firstOrNull() == output
				if (isMatch) add(candidate)
			}
		}

		return instructions
			.asReversed()
			.fold(listOf(0L)) { candidates, instruction -> candidates.flatMap { findCandidateBits(it, instruction) } }
			.minOrNull()?.toString() ?: "fail"
	}

	data class Computer(val register: MutableMap<Char, Long>) {

		private val output = mutableListOf<Int>()
		private var pointer = 0

		fun jumpTo(index: Int) {
			pointer = index
		}

		companion object {

			fun withRegister(a: Long, b: Long, c: Long): Computer {
				val register = mutableMapOf('A' to a, 'B' to b, 'C' to c)
				return Computer(register)
			}
		}

		fun run(inputInstructions: List<Int>): List<Int> {

			val instructions = inputInstructions
				.chunked(2)
				.mapIndexed { index, (operator, operand) ->
					Instruction(
						id = index.toLong(),
						operator = operator,
						operand = operand,
						updateRegister = { key, value -> register[key] = value },
						jumpTo = ::jumpTo,
						out = { output.add(it) }
					)
				}

			while (pointer in instructions.indices) {
				val instruction = instructions[pointer]
				if (instruction.execute(register)) {
					pointer++
				}
			}
			return output
		}
	}

	data class Instruction(
		val id: Long,
		val operator: Int,
		val operand: Int,
		val updateRegister: ((Char, Long) -> Unit),
		val jumpTo: ((Int) -> Unit),
		val out: ((Int) -> Unit)
	) {

		fun execute(register: Map<Char, Long>): Boolean {
			when (operator) {
				0 -> updateRegister('A', register['A']!! / (2.0.pow(getComboOperand(register).toDouble()).toLong()))
				1 -> updateRegister('B', register['B']!!.xor(operand.toLong()))
				2 -> updateRegister('B', getComboOperand(register) % 8)
				3 -> if (register['A']!! > 0) jumpTo((operand / 2)).also { return false }
				4 -> updateRegister('B', register['B']!!.xor(register['C']!!))
				5 -> out((getComboOperand(register) % 8).toInt())
				6 -> updateRegister('B', register['A']!! / (2.0.pow(getComboOperand(register).toDouble()).toLong()))
				7 -> updateRegister('C', register['A']!! / (2.0.pow(getComboOperand(register).toDouble()).toLong()))
			}
			return true
		}

		private fun getComboOperand(register: Map<Char, Long>): Long {
			return when (operand) {
				in 0..3 -> operand.toLong()
				4 -> register['A']!!
				5 -> register['B']!!
				6 -> register['C']!!
				else -> error("Invalid program")
			}
		}
	}
}
