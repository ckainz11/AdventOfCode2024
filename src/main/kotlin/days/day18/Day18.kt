package days.day18

import setup.Day
import util.*

class Day18(override val input: String) : Day<String>(input) {

	private val part = if (input.lines().size > 100) 2 else 1

	private val maxX = if (part == 1) 6 else 70
	private val maxY = if (part == 1) 6 else 70

	private val numOfFallenBytes = if (part == 1) 12 else 1024
	private val bytes = input.lines().map { it.allInts().let { (x, y) -> Point(x, y) } }

	private val fallingBytes = bytes.take(numOfFallenBytes)

	private val grid = emptyMatrixOf(maxX + 1, maxY + 1, false)

	private val startNode = Node(Point(0, 0), 0) { it inBoundsOf grid && it !in fallingBytes }
	private val end = Point(maxX, maxY)

	override fun solve1(): String = ImplicitGraph.withStartNode(startNode)
		.shortestPathToOrNull(end)?.toString() ?: "No path found"

	override fun solve2(): String {
		var low = 0
		var high = bytes.size - 1
		while (low < high) {
			val mid = low + (high - low) / 2

			val fallenBytes = bytes.take(mid).toSet()
			val startNode = Node(Point(0, 0), 0) { it inBoundsOf grid && it !in fallenBytes }
			val found = ImplicitGraph.withStartNode(startNode).shortestPathToOrNull(end) == null

			if (found) high = mid
			else low = mid + 1
		}
		return bytes[low - 1].let { "${it.x},${it.y}" }
	}

	data class Node(val position: Point, override var distance: Int, private val valid: ((Point) -> Boolean)) : ImplicitNode<Point, Node> {

		override val key = position

		override fun getAdjacentNodes(): List<Node> {
			return position
				.cardinalNeighbors()
				.filter { valid(it) }
				.map { Node(it, 1, valid) }
		}
	}
}
