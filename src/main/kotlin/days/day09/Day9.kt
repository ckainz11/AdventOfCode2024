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

	private var lastBlockEnd = 0
	private val blocks = input.dropLast(1).chunked(2).flatMapIndexed { index, s ->
		s.map { it.digitToInt() }.let { (occupied, free) ->
			listOf(
				Block(index, occupied, (lastBlockEnd..<lastBlockEnd + occupied)),
				Block(-1, free, (lastBlockEnd + occupied..<(lastBlockEnd + occupied + free)))
					.also { lastBlockEnd = it.indices.last + 1 }
			)
		}
	}.let {
		it + Block(input.length / 2, input.last().digitToInt(), (lastBlockEnd..lastBlockEnd + input.last().digitToInt()))
	}.filter { it.size > 0 }

	private val freeBlocks = ArrayDeque(blocks.filter { it.isFree() })
	private val filledBlocks = ArrayDeque(blocks.filter { !it.isFree() }.reversed())
	private val blockedFileSystem = filledBlocks.associateBy { it.indices }.toMutableMap()

	data class Block(val id: Int, var size: Int, var indices: IntRange) {

		fun isFree() = id == -1

		fun subtract(block: Block) {
			size -= block.size

			indices =
				if (size == 1) indices.last..indices.last
				else indices.first + block.size..<indices.last
		}
	}

	override fun solve2(): Long {

		while (freeBlocks.isNotEmpty()) {
			val freeBlock = freeBlocks.removeFirst()

			val tooLargeBlocks = mutableListOf<Block>()
			filled@
			while (filledBlocks.isNotEmpty()) {
				val filledBlock = filledBlocks.removeFirst()

				if (freeBlock.indices.last > filledBlock.indices.first)
					break

				// try next block
				if (filledBlock.size > freeBlock.size) {
					tooLargeBlocks.add(filledBlock)
					continue@filled
				}

				if (filledBlock.size == freeBlock.size) {
					blockedFileSystem.remove(filledBlock.indices)
					blockedFileSystem[freeBlock.indices] = filledBlock
					break@filled
				}

				blockedFileSystem.remove(filledBlock.indices)

				// move as much as possible
				blockedFileSystem[freeBlock.indices.first..<freeBlock.indices.first + filledBlock.size] = filledBlock

				// update free block
				freeBlock.subtract(filledBlock)
				freeBlocks.addFirst(freeBlock)
				break@filled
			}
			tooLargeBlocks.asReversed().forEach { filledBlocks.addFirst(it) }
		}

		return blockedFileSystem.map { (indices, block) -> if (block.id == -1) 0L else indices.sumOf { it.toLong() * block.id } }.sum()
	}

}
