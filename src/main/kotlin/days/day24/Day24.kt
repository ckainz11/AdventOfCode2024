package days.day24

import setup.Day
import util.join
import util.sections

class Day24(override val input: String) : Day<String>(input) {

	private enum class Operation { AND, OR, XOR }

	private data class Gate(val a: String, val b: String, val op: Operation, var out: String)

	override fun solve1() = parse(input).let { (gates, registers) -> run(gates, registers) }.toString()

	// credit: https://www.reddit.com/r/adventofcode/comments/1hla5ql/2024_day_24_part_2_a_guide_on_the_idea_behind_the/
	override fun solve2(): String {
		val (gates, registers) = parse(input)

		val nxz = gates.filter { it.out.first() == 'z' && it.out != "z45" && it.op != Operation.XOR }
		val xnz = gates.filter { it.a.first() !in "xy" && it.b.first() !in "xy" && it.out.first() != 'z' && it.op == Operation.XOR }

		for (i in xnz) {
			val b = nxz.first { it.out == gates.firstZThatUsesC(i.out) }
			val temp = i.out
			i.out = b.out
			b.out = temp
		}

		val falseCarry = (getWiresAsLong(registers, 'x') + getWiresAsLong(registers, 'y') xor run(gates, registers)).countTrailingZeroBits().toString()
		return (nxz + xnz + gates.filter { it.a.endsWith(falseCarry) && it.b.endsWith(falseCarry) }).map { it.out }.sorted().join(",")
	}

	private fun List<Gate>.firstZThatUsesC(c: String): String? {
		val x = filter { it.a == c || it.b == c }
		x.find { it.out.startsWith('z') }?.let { return "z" + (it.out.drop(1).toInt() - 1).toString().padStart(2, '0') }
		return x.firstNotNullOfOrNull { firstZThatUsesC(it.out) }
	}

	private fun run(gates: List<Gate>, registers: MutableMap<String, Int>): Long {

		fun traverse(curr: String): Int {
			val gate = gates.find { it.out == curr }!!
			val a = registers.getOrPut(gate.a) { traverse(gate.a) }
			val b = registers.getOrPut(gate.b) { traverse(gate.b) }
			return when (gate.op) {
				Operation.AND -> a and b
				Operation.OR -> a or b
				Operation.XOR -> a xor b
			}
		}
		gates.forEach { registers[it.out] = traverse(it.out) }
		return getWiresAsLong(registers, 'z')
	}

	private fun getWiresAsLong(registers: MutableMap<String, Int>, type: Char) =
		registers.filter { it.key.startsWith(type) }.toList()
			.sortedBy { it.first }
			.joinToString("") { it.second.toString() }.reversed().toLong(2)

	private fun parse(input: String): Pair<MutableList<Gate>, MutableMap<String, Int>> = input.sections().map { it.lines() }.let { (initial, path) ->
		path.map { line -> line.split(" ").let { Gate(it[0], it[2], Operation.valueOf(it[1]), it[4]) } }.toMutableList() to
		initial.associate { line -> line.split(": ").let { it.first() to it.last().toInt() } }.toMutableMap()
	}
}
