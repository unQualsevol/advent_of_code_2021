import java.io.File
import kotlin.math.abs

val graph = File("input").readLines().map { line -> line.toCharArray().map { it.toString().toInt() } }
val maxY = graph.size
val extendedMaxY = maxY * 5
val maxX = graph[0].size
val extendedMaxX = maxX * 5

val lowestRisk = aStarSearch(Coordinate(0,0), Coordinate(extendedMaxY-1, extendedMaxX-1))
println("Lowest total risk of any path from the top left to the bottom right is: ${lowestRisk}")

fun aStarSearch(start: Coordinate, finish: Coordinate):Int {

    val openVertices = mutableSetOf(start)
    val closedVertices = mutableSetOf<Coordinate>()
    val costFromStart = mutableMapOf(start to 0)
    val estimatedTotalCost = mutableMapOf(start to heuristicDistance(start, finish))

    while (openVertices.size > 0) {

        val currentPos = openVertices.minByOrNull { estimatedTotalCost.getValue(it) }!!

        if (currentPos == finish) {
            return estimatedTotalCost.getValue(finish)
        }

        openVertices.remove(currentPos)
        closedVertices.add(currentPos)

        getNeighbours(currentPos)
            .filterNot { closedVertices.contains(it) }
            .forEach { neighbour ->
                val score = costFromStart.getValue(currentPos) + calculateValue(neighbour)
                if (score < costFromStart.getOrDefault(neighbour, Int.MAX_VALUE)) {
                    if (!openVertices.contains(neighbour)) {
                        openVertices.add(neighbour)
                    }
                    costFromStart[neighbour] = score
                    estimatedTotalCost[neighbour] = score + heuristicDistance(neighbour, finish)
                }
            }
    }
    throw IllegalArgumentException("No Path from Start $start to Finish $finish")
}

fun heuristicDistance(start: Coordinate, finish: Coordinate): Int {
    val dx = abs(start.y - finish.y)
    val dy = abs(start.x - finish.x)
    return (dx + dy) + (-2) * minOf(dx, dy)
}

fun getNeighbours(node: Coordinate): List<Coordinate> {
    return listOf(
        Coordinate(node.y+1, node.x),
        Coordinate(node.y, node.x+1),
        Coordinate(node.y-1, node.x),
        Coordinate(node.y, node.x-1)
    ).filter { isValid(it)}
}

fun isValid(node:Coordinate): Boolean {
    return node.y in 0 until extendedMaxY && node.x in 0 until extendedMaxX
}

fun calculateValue(node: Coordinate): Int {
    val plusY = node.y / maxY
    val posY = node.y % maxY
    val plusX = node.x / maxX
    val posX = node.x % maxX
    val total = graph[posY][posX] + plusY + plusX
    return total % 10 + total / 10
}

data class Coordinate(val y:Int, val x:Int)
