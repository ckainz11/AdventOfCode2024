package days.day16

import setup.Day
import util.*
import java.util.*

typealias NodeKey = Pair<Point, Point>

class Day16(override val input: String) : Day<Int>(input) {

	private val grid = input.asMatrix()
	private val start = grid.matrixIndexOfFirst { it == 'S' }
	private val end = grid.matrixIndexOfFirst { it == 'E' }
	private val direction = Point.RIGHT

	override fun solve1(): Int {
		val startNode = NodeA(start, direction, 0, grid)
		val graph = ImplicitGraph(startNode)
		graph.dijkstraEarlyExit = { it.location == end }
		val distances = graph.dijkstra()
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
		val queue = PriorityQueue<Triple<List<Point>, Point, Int>>(compareBy { it.third })
		queue.add(Triple(listOf(start), Point.RIGHT, 0))

		var min = Int.MAX_VALUE
		val best = HashSet<Point>()
		val seen = HashMap<Pair<Point, Point>, Int>()

		while (queue.isNotEmpty()) {
			val (path, dir, score) = queue.poll()

			if (path.last() == end) {
				if (score <= min) min = score
				else return best.size
				best.addAll(path)
			}

			if (seen[path.last() to dir] != null && seen[path.last() to dir]!! < score) continue
			seen[path.last() to dir] = score

			if (grid[path.last() + dir] != '#')
				queue.add(Triple(path + (path.last() + dir), dir, score + 1))

			queue.add(Triple(path, dir.rotateClockwise(), score + 1000))
			queue.add(Triple(path, dir.rotateCounterClockwise(), score + 1000))
		}
		return 0
	}
}
