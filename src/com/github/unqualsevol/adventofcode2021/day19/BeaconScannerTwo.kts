import java.io.File
import kotlin.math.absoluteValue

val input = File("input").readText()
val scanners = input.split("\n\n").map { scanner ->
    scanner.lines().drop(1).map { Coordinate.build(it) }.toSet()
}

println(solve().scanners.pairs().maxOf { it.first distanceTo it.second })

fun <T> Collection<T>.pairs(): List<Pair<T,T>> =
    this.flatMapIndexed { index, a ->
        this.drop(index).map { b -> a to b }
    }

fun findTransformIfIntersects(left: Set<Coordinate>, right: Set<Coordinate>): Transform? =
    (0 until 6).firstNotNullOfOrNull { face ->
        (0 until 4).firstNotNullOfOrNull { rotation ->
            val rightReoriented = right.map { it.face(face).rotate(rotation) }.toSet()
            left.firstNotNullOfOrNull { s1 ->
                rightReoriented.firstNotNullOfOrNull { s2 ->
                    val difference = s1 - s2
                    val moved = rightReoriented.map { it + difference }.toSet()
                    if (moved.intersect(left).size >= 12) {
                        Transform(difference, moved)
                    } else null
                }
            }
        }
    }

fun solve(): Solution {
    val baseSector = scanners.first().toMutableSet()
    val foundScanners = mutableSetOf(Coordinate(0,0,0))
    val unmappedSectors = ArrayDeque<Set<Coordinate>>().apply { addAll(scanners.drop(1)) }
    while(unmappedSectors.isNotEmpty()) {
        val thisSector = unmappedSectors.removeFirst()
        when (val transform = findTransformIfIntersects(baseSector, thisSector)) {
            null -> unmappedSectors.add(thisSector)
            else -> {
                baseSector.addAll(transform.beacons)
                foundScanners.add(transform.scanner)
            }
        }
    }
    return Solution(foundScanners, baseSector)
}

class Transform(val scanner: Coordinate, val beacons: Set<Coordinate>)

class Solution(val scanners: Set<Coordinate>, val beacons: Set<Coordinate>)

data class Coordinate(val x: Int, val y: Int, val z: Int) {
     companion object {
         fun build(line: String): Coordinate =
             line.split(",").let { split ->
                 Coordinate(split[0].toInt(), split[1].toInt(), split[2].toInt())
             }
     }

    operator fun plus(other: Coordinate): Coordinate =
        Coordinate(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Coordinate): Coordinate =
        Coordinate(x - other.x, y - other.y, z - other.z)

    infix fun distanceTo(other: Coordinate): Int =
        (this.x - other.x).absoluteValue + (this.y - other.y).absoluteValue + (this.z - other.z).absoluteValue

    fun face(facing: Int): Coordinate =
        when (facing) {
            0 -> this
            1 -> Coordinate(x, -y, -z)
            2 -> Coordinate(x, -z, y)
            3 -> Coordinate(-y, -z, x)
            4 -> Coordinate(y, -z, -x)
            5 -> Coordinate(-x, -z, -y)
            else -> error("Invalid facing")
        }

    fun rotate(rotating: Int): Coordinate =
        when (rotating) {
            0 -> this
            1 -> Coordinate(-y, x, z)
            2 -> Coordinate(-x, -y, z)
            3 -> Coordinate(y, -x, z)
            else -> error("Invalid rotation")
        }
}