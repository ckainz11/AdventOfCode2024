package util

import java.util.*
import kotlin.collections.HashMap

class ImplicitGraph<K, N : ImplicitNode<K, N>>(private val start: N) {

	companion object {

		fun <K, N : ImplicitNode<K, N>> withStartNode(start: N) = ImplicitGraph(start)
	}

	/**
	 * Finds the shortest path to a node with the given key.
	 * @param key the key of the node to find the shortest path to.
	 * @return the length of the shortest path to the node with the given key, or null if there is no path.
	 */
	fun shortestPathToOrNull(key: K): Int? {
		val distances = dijkstra()
		return distances[key]
	}

	/**
	 * Finds the shortest path to a node with the given key.
	 * @param key the key of the node to find the shortest path to.
	 * @return the length of the shortest path to the node with the given key.
	 * @throws IllegalStateException if there is no path to the node with the given key.
	 */
	fun shortestPathTo(key: K): Int {
		val distances = dijkstra { it.key == key }
		return distances[key] ?: error("No path to $key")
	}

	/**
	 * Finds the shortests paths to all nodes in the graph.
	 * @param earlyExitIf a function that returns true if the algorithm should stop early.
	 * @return a map of the shortest paths to all nodes in the graph.
	 */
	fun dijkstra(earlyExitIf: ((N) -> Boolean)? = null): Map<K, Int> {
		val distances = HashMap<K, Int>()
		val queue = PriorityQueue<N>()
		val visited = mutableSetOf<K>()

		distances[start.key] = start.distance
		queue.add(start)

		while (queue.isNotEmpty()) {
			val current = queue.poll()

			if (current.key in visited) continue
			visited.add(current.key)

			if (earlyExitIf != null && earlyExitIf(current))
				break

			queue.addAll(current.getAdjacentNodes()
				.filter {
					it.key !in visited &&
					distances[current.key]!! + it.distance < (distances[it.key] ?: Int.MAX_VALUE)
				}
				.map { node -> node.apply { distance = current.distance + node.distance } }
				.onEach { node -> distances[node.key] = node.distance }
			)
		}

		return distances
	}
}

/**
 * An implicit graph node.
 * @property distance the cost to reach this node from the previous node.
 * @property getAdjacentNodes a function that returns the nodes that are adjacent to this node.
 */
interface ImplicitNode<K, N : ImplicitNode<K, N>> : Comparable<N> {

	var distance: Int
	fun getAdjacentNodes(): List<N>
	override fun compareTo(other: N) = distance.compareTo(other.distance)
	val key: K
}
