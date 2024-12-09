package days.day09

import days.Day

class Day9(override val input: String) : Day<Long>(input) {

	private val fileSystem = (input.dropLast(1).chunked(2)
		.flatMapIndexed { index, s ->
			(1..s[0].digitToInt()).map { index.toLong() } + List(s[1].digitToInt()) { -1L }
		} + (1..input.last().digitToInt()).map { (input.length / 2).toLong() })
		.toMutableList()

	override fun solve1(): Long {
		val freeIndices = ArrayDeque(fileSystem.mapIndexedNotNull { index, i -> if (i == -1L) index else null })
		val nums = ArrayDeque(fileSystem.mapIndexed { index, i -> index to i }.filter { it.second != -1L }.reversed())

		while (freeIndices.isNotEmpty()) {
			val freeIndex = freeIndices.removeFirst()
			val (oldIndex, toPlace) = nums.removeFirst()
			if (freeIndex > oldIndex)
				break
			fileSystem[freeIndex] = toPlace
			fileSystem[oldIndex] = -1
			freeIndices.addLast(oldIndex)
		}

		return fileSystem.mapIndexed { index, i -> if (i == -1L) 0L else index * i }.sum()
	}

	private val blocks = (input.dropLast(1).chunked(2).flatMapIndexed { index: Int, s: String ->
		listOf(
			Block(s[0].digitToInt(), index),
			Block(s[1].digitToInt(), -1)
		)
	} + Block(input.last().digitToInt(), input.length / 2)).filter { it.size != 0 }.toMutableList()

	override fun solve2(): Long {
		var i = blocks.size - 1
		while (i >= 0) {
			val toMove = blocks[i]
			if (toMove.isFree()) {
				i--
				continue
			}

			for (j in 0..<i) {
				val freeBlock = blocks[j]

				if (!freeBlock.isFree() || freeBlock.size < toMove.size)
					continue

				blocks.add(j, toMove)
				val newSize = freeBlock.size - toMove.size
				if (newSize > 0) {
					blocks[j + 1] = Block(newSize, -1)
					i++
				} else
					blocks.removeAt(j + 1)

				blocks[i] = Block(toMove.size, -1)
				break
			}
			i--
		}
		var checksum = 0L
		var index = 0
		for (block in blocks) {
			for (x in 0..<block.size) {
				checksum += if (block.isFree()) 0 else index * block.id
				index++
			}
		}
		return checksum
	}

	data class Block(val size: Int, val id: Int) {

		fun isFree() = id == -1

		override fun toString(): String {
			return (0..<size).joinToString("") { if (isFree()) "." else id.toString() }
		}
	}

}
