import java.io.File
import java.util.*

val heightMap = File("input").readLines().map { line -> line.toCharArray().map { it.code - 48 } }
val maxY = heightMap.size
val maxX = heightMap[0].size

val basinsSize = mutableListOf<Int>()
for (y in 0 until maxY) {
    for(x in 0 until maxX) {
        if(isLowPoint(y, x)) {
            basinsSize.add(calculateBasinSize(y,x))
        }
    }
}
basinsSize.sortDescending()
val result = basinsSize.subList(0,3).reduce { acc, i -> acc*i }
println("The product of the three largest basins: $result")

fun isLowPoint(y: Int, x: Int): Boolean {
    val value = heightMap[y][x]
    val adjacent = listOf(safeValue(y-1, x), safeValue(y, x+1), safeValue(y+1, x), safeValue(y, x-1))
    return adjacent.all { value < it }
}

fun safeValue(y: Int, x: Int): Int {
    if(y < 0 || y >= maxY || x < 0 || x >= maxX) return 9
    return heightMap[y][x]
}

fun calculateBasinSize(y: Int, x: Int): Int {
    val bottom = Coordinate(y, x)
    val basin = mutableSetOf<Coordinate>()
    val queue: Queue<Coordinate> = LinkedList(listOf(bottom))
    while (queue.isNotEmpty()) {
        val current = queue.poll()
        basin.add(current)
        queue.addAll(getAdjacents(current).filter{isNotNine(it) && !basin.contains(it)})
    }
    return basin.size
}

data class Coordinate(val y:Int, val x:Int)

fun getAdjacents(coordinate: Coordinate): List<Coordinate> {
    val (y, x) = coordinate
    return listOf(Coordinate(y-1, x), Coordinate(y, x+1), Coordinate(y+1, x), Coordinate(y, x-1))
}

fun isNotNine(coordinate: Coordinate): Boolean {
    return safeValue(coordinate.y, coordinate.x) != 9
}
