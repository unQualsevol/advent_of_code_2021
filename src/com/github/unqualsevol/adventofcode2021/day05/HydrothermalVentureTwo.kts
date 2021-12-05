import java.io.File
import kotlin.math.absoluteValue


var map = mutableSetOf<Coordinate>()
var intersections = mutableSetOf<Coordinate>()
File("input").forEachLine { line ->
    val regex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
    val matchResult = regex.find(line)
    val (x1, y1, x2, y2) = matchResult!!.destructured
    val start = createCoordinate(x1, y1)
    val end = createCoordinate(x2, y2)
    val path = createPath(start, end)
    path.forEach {
        if (!map.add(it)) {
            intersections.add(it)
        }
    }

}
println("Number of overlapping spots: ${intersections.size}")

fun createCoordinate(x:String, y: String): Coordinate = Coordinate(x.toInt(), y.toInt())

fun createPath(start: Coordinate, end: Coordinate): List<Coordinate> {
    return when {
        start.x == end.x && start.y == end.y -> listOf(start)
        isDiagonal(start,end) -> createDiagonalPath(start, end)
        start.x == end.x -> createVerticalPath(start, end.y)
        start.y == end.y -> createHorizontalPath(start, end.x)
        else -> emptyList()
    }
}

fun createHorizontalPath(start:Coordinate, endX: Int): List<Coordinate> {
    val result = mutableListOf<Coordinate>()
    val range = if(start.x < endX) start.x..endX else endX..start.x
    for (i in range){
        result.add(Coordinate(i, start.y))
    }
    return result
}

fun createVerticalPath(start:Coordinate, endY: Int): List<Coordinate> {
    val result = mutableListOf<Coordinate>()
    val range = if(start.y < endY) start.y..endY else endY..start.y
    for (i in range){
        result.add(Coordinate(start.x, i))
    }
    return result
}

fun isDiagonal(start:Coordinate, end: Coordinate): Boolean =
    (start.x - end.x).absoluteValue == (start.y - end.y).absoluteValue

fun createDiagonalPath(start:Coordinate, end: Coordinate): List<Coordinate> {
    val result = mutableListOf<Coordinate>()
    val distance = (start.x-end.x).absoluteValue
    val xDirection = if(start.x < end.x) 1 else -1
    val yDirection = if(start.y < end.y) 1 else -1
    for (i in 0..distance) {
        result.add(Coordinate(start.x+(i*xDirection), start.y+(i*yDirection)))
    }
    return result
}

data class Coordinate(val x: Int, val y: Int)

