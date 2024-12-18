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

	private val start = NodeA(Point(0, 0), 0) { it inBoundsOf grid && it !in fallingBytes }
	private val end = Point(maxX, maxY)

	override fun solve1(): String {
		val graph = ImplicitGraph(start)
		graph.dijkstraEarlyExit = { it.position == end }
		val distances = graph.dijkstra()
		val steps = distances[end] ?: -1
		return steps.toString()
	}

	override fun solve2(): String = (1..<bytes.size).map { bytes.take(numOfFallenBytes + it) }
		.first { fallenBytes ->
			val graph = ImplicitGraph(NodeA(Point(0, 0), 0) { it inBoundsOf grid && it !in fallenBytes })
			graph.dijkstraEarlyExit = { it.position == end }
			val distances = graph.dijkstra()
			val steps = distances[end] ?: -1
			steps == -1
		}.last().let { "${it.x},${it.y}" }

	data class NodeA(val position: Point, override var distance: Int, private val filter: ((Point) -> Boolean)) : ImplicitNode<Point, NodeA> {

		override val key = position

		override fun getAdjacentNodes(): List<NodeA> {
			return position
				.cardinalNeighbors()
				.filter { filter(it) }
				.map { NodeA(it, 1, filter) }
		}
	}
}
