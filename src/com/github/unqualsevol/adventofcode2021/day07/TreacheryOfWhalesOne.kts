import java.io.File
import kotlin.math.absoluteValue

val positions = File("input").readText().split(",").map { it.toInt() }
val max = positions.maxOrNull()!!
val min = positions.minOrNull()!!

val consumes = mutableListOf<Int>()
for (i in min..max) {
    consumes.add(positions.sumOf { calculateDistance(it, i) })
}

println("Fuel spend to align is: ${consumes.minOrNull()}")

fun calculateDistance(start: Int, end: Int): Int {
    return (start-end).absoluteValue
}