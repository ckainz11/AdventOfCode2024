package util

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class ImplicitGraph<K, N : ImplicitNode<K, N>> {

	/**
	 * If this function returns true, the dijkstra algorithm will exit early.
	 * This is useful, for example, if you only want to find the shortest path to a specific node.
	 */
	var dijkstraEarlyExit: ((N) -> Boolean)? = null
	private fun dijkstraEarlyExit(node: N) = dijkstraEarlyExit?.invoke(node) ?: false

	fun dijkstra(start: N): Map<K, Int> {
		val distances = HashMap<K, Int>()
		val visited = HashSet<K>()
		val queue = PriorityQueue<N>()

		distances[start.key] = start.distance
		queue.add(start)

		while (queue.isNotEmpty()) {
			val current = queue.poll()

			if (dijkstraEarlyExit(current)) {
				distances[current.key] = current.distance
				break
			}

			if (current.key in distances && distances[current.key]!! < current.distance) continue

			distances[current.key] = current.distance

			queue.addAll(current.getAdjacentNodes().map { node -> node.apply { distance = current.distance + node.distance } })
		}

		return distances
	}
}

/**
 * An implicit graph node.
 * @param distance the cost to reach this node from the previous node.
 * @param getAdjacentNodes a function that returns the nodes that are adjacent to this node.
 */
interface ImplicitNode<K, N : ImplicitNode<K, N>> : Comparable<N> {

	var distance: Int
	fun getAdjacentNodes(): List<N>
	override fun compareTo(other: N) = distance.compareTo(other.distance)
	val key: K
}
