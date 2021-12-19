import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val input = File("input").readText()

val regex = """target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)""".toRegex()
val matches = regex.find(input)
val (xStartString, xEndString, yStartString, yEndString) = matches!!.destructured
val xStart = xStartString.toInt()
val xEnd = xEndString.toInt()
val yStart = yStartString.toInt()
val yEnd = yEndString.toInt()
val xHitRange = xStart..xEnd
val yHitRange = yStart..yEnd

val xVelocityTestRange = 1..xEnd
val yVelocityTestRange = min(yStart, yEnd) until max(abs(yStart), abs(yEnd))

var count = 0
for (i in xVelocityTestRange){
    for (j in yVelocityTestRange){
        if(trajectoryHits(calculateTrajectory(Coordinate(i,j)))) count++
    }
}
println("Number of distinct initial velocities that cause the probe to hit the target area: $count")

fun calculateTrajectory(initialVelocity: Coordinate): List<Coordinate>
{
    val trajectory = mutableListOf<Coordinate>()

    var position = Coordinate(0,0)
    var velocity = initialVelocity
    while(position.x < xEnd && position.y > yEnd) {
        val result = step(position, velocity)
        position = result.first
        velocity = result.second
        trajectory.add(position)
    }
    return trajectory
}

fun step(position: Coordinate, velocity: Coordinate): Pair<Coordinate, Coordinate> {
    val newPosition = calculateNewPosition(position, velocity)
    val newVelocity = calculateNewVelocity(velocity)
    return Pair(newPosition, newVelocity)
}

fun trajectoryHits(coordinates:List<Coordinate>): Boolean = coordinates.any { hits(it) }


fun hits(position: Coordinate): Boolean = position.x in xHitRange && position.y in yHitRange

data class Coordinate(val x:Int, val y:Int)

fun calculateNewVelocity(velocity: Coordinate): Coordinate {
    return Coordinate(drag(velocity.x), velocity.y-1)
}

fun calculateNewPosition(
    position: Coordinate,
    velocity: Coordinate
): Coordinate {
    return Coordinate(position.x+velocity.x, position.y+velocity.y)
}

fun drag(x: Int): Int {
    return when {
        x > 0 -> x-1
        x < 0 -> x+1
        else -> 0
    }
}
