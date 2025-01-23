package days.day25

import setup.Day
import util.Matrix
import util.asMatrix
import util.columns
import util.sections

class Day25(override val input: String) : Day<Int>(input) {

	private val schematics = input.sections().map { it.asMatrix() }.map { Schematic.fromMatrix(it) }
	private val keys = schematics.filter { it.type == SchematicType.KEY }
	private val locks = schematics.filter { it.type == SchematicType.LOCK }

	override fun solve1(): Int = keys.sumOf { key -> locks.count { lock -> key.fits(lock) } }

	override fun solve2(): Int = 0

	data class Schematic(val matrix: Matrix<Char>, val type: SchematicType) {

		companion object {

			fun fromMatrix(matrix: Matrix<Char>) = Schematic(matrix, SchematicType.fromSchematic(matrix))
		}

		private val height = matrix.size

		fun fits(other: Schematic): Boolean {
			if (other.type == this.type)
				error("Schematics must be of different types")

			val colHeights = matrix.columns().map { col -> col.count { it == '#' } }
			val otherColHeights = other.matrix.columns().map { col -> col.count { it == '#' } }

			return colHeights.zip(otherColHeights).all { (a, b) -> a + b <= height }
		}
	}

	enum class SchematicType {
		LOCK,
		KEY;

		companion object {

			fun fromSchematic(schematic: Matrix<Char>) = when {
				schematic.last().all { it == '#' } -> KEY
				schematic.first().all { it == '#' } -> LOCK
				else -> throw IllegalArgumentException("Invalid schematic")
			}
		}
	}
}
