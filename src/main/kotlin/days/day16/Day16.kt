package days.day16

import setup.Day
import util.*

typealias NodeKey = Pair<Point, Point>

class Day16(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix()
	private val start = grid.matrixIndexOfFirst { it == 'S' }
	private val end = grid.matrixIndexOfFirst { it == 'E' }
	private val direction = Point.RIGHT

	override fun solve1(): Int {
		val graph = ImplicitGraph<NodeKey, NodeA>()

		graph.dijkstraEarlyExit = { it.location == end }

		val startNode = NodeA(start, direction, 0, grid)
		val distances = graph.dijkstra(startNode)

		return distances.filter { it.key.first == end }.values.min()
	}

	class NodeA(val location: Point, private val direction: Point, override var distance: Int, private val grid: Matrix<Char>) : ImplicitNode<NodeKey, NodeA> {

		override val key = location to direction

		override fun getAdjacentNodes(): List<NodeA> = buildList {
			if (grid[location + direction] != '#')
				add(NodeA(location + direction, direction, 1, grid))
			add(NodeA(location, direction.rotateClockwise(), 1000, grid))
			add(NodeA(location, direction.rotateCounterClockwise(), 1000, grid))
		}
	}

	override fun solve2(): Int {
		val graph = ImplicitGraph<NodeKey, NodeB>()

		var min = Int.MAX_VALUE
		val bestPath = mutableSetOf<Point>()

		graph.dijkstraEarlyExit = { node ->
			var endSearch = false
			if (node.path.last() == end) {
				if (node.distance <= min) {
					min = node.distance
					bestPath.addAll(node.path)
				} else endSearch = true
			}
			endSearch
		}

		val startNode = NodeB(listOf(start), direction, 0) { p -> grid[p].isWall() }
		graph.dijkstra(startNode)
		return bestPath.size
	}

	class NodeB(val path: List<Point>, private val direction: Point, override var distance: Int, private val wallAt: ((Point) -> Boolean)) : ImplicitNode<NodeKey, NodeB> {

		override val key = path.last() to direction

		override fun getAdjacentNodes(): List<NodeB> = buildList {
			if (!wallAt(path.last() + direction)) {
				val next = path.last() + direction
				add(NodeB(path + next, direction, 1, wallAt))
			}
			add(NodeB(path, direction.rotateClockwise(), 1000, wallAt))
			add(NodeB(path, direction.rotateCounterClockwise(), 1000, wallAt))
		}
	}

	private fun Char.isWall(): Boolean = this == '#'
}
