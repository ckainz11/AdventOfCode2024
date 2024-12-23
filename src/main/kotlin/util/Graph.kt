package util

/**
 * A standard graph implementation with nodes and edges.
 */
data class Graph<N : Node, E : Edge<N>>(val nodes: Set<N>, val edges: List<E>) {

	val adjacencyList = mutableMapOf<N, Set<N>>()

	init {
		nodes.forEach { node ->
			adjacencyList[node] = edges.filter { it.from == node }.map { it.to }.toSet()
		}
	}

	/**
	 * Bron-Kerbosch algorithm to find all maximal cliques in the graph.
	 */
	fun bronKerbosch(): Set<Set<N>> {
		val result = mutableSetOf<Set<N>>()
		bronKerbosch(mutableSetOf(), nodes.toMutableSet(), mutableSetOf(), result)
		return result
	}

	private fun bronKerbosch(r: Set<N>, p: MutableSet<N>, x: MutableSet<N>, result: MutableSet<Set<N>>) {
		if (p.isEmpty() && x.isEmpty())
			result.add(r.toSet())

		val pivot = (p + x).maxByOrNull { adjacencyList[it]?.size ?: 0 }
		val neighbors = adjacencyList.getOrDefault(pivot, emptySet())
		val toExplore = p - neighbors

		toExplore.forEach { node ->
			val newR = (r + node).toMutableSet()
			val newP = p.intersect(adjacencyList[node] ?: emptySet()).toMutableSet()
			val newX = x.intersect(adjacencyList[node] ?: emptySet()).toMutableSet()
			bronKerbosch(newR, newP, newX, result)
			p -= node
			x += node
		}
	}
}

interface Node
interface Edge<N : Node> {

	val from: N
	val to: N
	val weight: Int
}
