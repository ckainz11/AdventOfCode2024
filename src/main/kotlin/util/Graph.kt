package util

import java.util.*

/**
 * A standard graph implementation with nodes and edges.
 */
data class Graph<N : Node, E : Edge<N>>(val nodes: List<N>, val edges: List<E>) {

	private val adjacencyList = mutableMapOf<N, List<E>>()

	/*
	 * If this function returns true, the algorithm will stop early.
	 */
	var dijkstraEarlyExit: ((N) -> Boolean)? = null
	private fun dijkstraEarlyExit(node: N) = dijkstraEarlyExit?.invoke(node) ?: false

	init {
		nodes.forEach { node ->
			adjacencyList[node] = edges.filter { it.from == node }
		}
	}

	fun dijkstra(start: N): Map<N, Int> {
		val distances = mutableMapOf<N, Int>()
		val visited = mutableSetOf<N>()
		val queue = PriorityQueue<Pair<N, Int>>(compareBy { it.second })

		distances[start] = 0
		queue.add(start to 0)

		while (queue.isNotEmpty()) {
			val (current) = queue.poll()

			if (dijkstraEarlyExit(current)) break

			if (current in visited) continue

			visited.add(current)

			adjacencyList[current]?.forEach { edge ->
				val newDistance = distances[current]!! + edge.weight
				if (distances[edge.to] == null || newDistance < distances[edge.to]!!) {
					distances[edge.to] = newDistance
					queue.add(edge.to to newDistance)
				}
			}
		}

		return distances
	}
}

interface Node
interface Edge<N : Node> {

	val from: N
	val to: N
	val weight: Int
}
