import java.io.File
import kotlin.math.abs

val input = File("input").readText()

val regex = """target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)""".toRegex()
val matches = regex.find(input)
val (xStart, xEnd, yStart, yEnd) = matches!!.destructured
val maxInitialY = maxOf(abs(yStart.toInt()), abs(yEnd.toInt()))-1
val highestYPosition = (maxInitialY * (maxInitialY+1))/2
println("Highest Y position reached by any hitting trajectory: $highestYPosition")
