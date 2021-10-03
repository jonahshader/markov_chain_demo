package jonahshader.graph

import java.util.*

typealias Node = Char
class DirectedGraph {
    private val edges = mutableMapOf<String, Edge>()
    private val nodeOutputs = mutableMapOf<Node, MutableSet<Edge>>()
    private val nodeInputs = mutableMapOf<Node, MutableSet<Edge>>()

    fun add(start: Node, end: Node) {
        val key = nodesToKey(start, end)
        val edge: Edge
        if (edges.containsKey(key)){
            edge = edges[key]!!
            edge.frequency++
        } else {
            val newEdge = Edge(start, end)
            edge = newEdge
            newEdge.frequency++
            edges[key] = newEdge
        }

        if (nodeOutputs.containsKey(start)) {
            nodeOutputs[start]!!.add(edge)
        } else {
            val newSet = mutableSetOf<Edge>()
            newSet.add(edge)
            nodeOutputs[start] = newSet
        }

        if (nodeInputs.containsKey(end)) {
            nodeInputs[end]!!.add(edge)
        } else {
            val newSet = mutableSetOf<Edge>()
            newSet.add(edge)
            nodeInputs[end] = newSet
        }
    }

    fun getNext(current: Node, rand: Random) : Node? {
        if (nodeOutputs.containsKey(current)) {
            val possibleOutputs = nodeOutputs[current]
            val outputsSum = possibleOutputs!!.fold(0) { acc, edge -> acc + edge.frequency }
            if (outputsSum == 0) {
                return null
            } else {
                val nextNum = rand.nextInt(outputsSum)
                var runningSum = 0
                possibleOutputs.forEach {
                    runningSum += it.frequency
                    if (nextNum < runningSum) {
                        return it.end
                    }
                }
                // should never get here
                return possibleOutputs.random().end
            }
        } else {
            return null
        }
    }

    private fun nodesToKey(start: Node, end: Node) = "$start$end"
}

