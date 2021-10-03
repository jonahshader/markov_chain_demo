package jonahshader

import jonahshader.graph.DirectedGraph
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.random.Random

fun main() {
    val file = File("input.txt")
    val reader = BufferedReader(FileReader(file))
    val builder = StringBuilder()
    reader.lines().forEach {
        builder.append(it).append('\n')
    }
    builder.deleteCharAt(builder.length - 1)
    val inputString = builder.toString()

    val javaRand = java.util.Random()
    val kotlinRand: kotlin.random.Random = Random(javaRand.nextLong())
    val graph = DirectedGraph()

    if (inputString.length > 2) {
//        println("Input string:")
//        println(inputString)

        for (i in 1 until inputString.length) {
            graph.add(inputString[i - 1], inputString[i])
        }


        val startingChar = inputString.toCharArray().random(kotlinRand)
        val outputBuilder = StringBuilder()
        outputBuilder.append(startingChar)
        for (i in 0 until inputString.length - 1) {
            outputBuilder.append(graph.getNext(outputBuilder.last(), javaRand))
        }

        println("Output string:")
        println(outputBuilder)
    }
}