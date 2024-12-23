package days.day23

import setup.Day
import util.Edge
import util.Graph
import util.Node

class Day23(override val input: String) : Day<String>(input) {

	private val nodes = input.lines().flatMap { line -> line.split("-").map { Computer(it) } }.toSet()
	private val edges = input.lines().flatMap { line ->
		line.split("-").let { (a, b) ->
			listOf(
				Connection(Computer(a), Computer(b)),
				Connection(Computer(b), Computer(a))
			)
		}
	}
	private val graph = Graph(nodes, edges)

	data class Computer(val name: String) : Node
	data class Connection(override val from: Computer, override val to: Computer, override val weight: Int = 0) : Edge<Computer>

	override fun solve1(): String = buildSet {
		nodes.filter { it.name.startsWith("t") }.map { start ->
			graph.adjacencyList[start]!!.forEach { middle ->
				graph.adjacencyList[middle]!!.forEach { end ->
					if (graph.adjacencyList[end]!!.contains(start))
						add(setOf(start, middle, end))
				}
			}
		}
	}.size.toString()

	override fun solve2(): String = graph.bronKerbosch()
		.maxBy { it.size }
		.sortedBy { it.name }
		.joinToString(",") { it.name }

}
