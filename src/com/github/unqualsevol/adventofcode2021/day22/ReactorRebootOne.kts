import java.io.File

val validRange = -50..50
val cubesOn = mutableSetOf<Cubes>()
File("input").readLines().forEach { line ->
    val regex = """(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)""".toRegex()
    val matchResult = regex.find(line)
    val (onOff , x1, x2, y1, y2, z1, z2) = matchResult!!.destructured
    val xStart = x1.toInt()
    val xEnd = x2.toInt()
    val yStart = y1.toInt()
    val yEnd = y2.toInt()
    val zStart = z1.toInt()
    val zEnd = z2.toInt()
    if (listOf(xStart, xEnd, yStart, yEnd, zStart, zEnd).all { it in validRange }) {
        val newCells = generateCubes(xStart, xEnd, yStart, yEnd, zStart, zEnd)
        if (onOff == "on") {
            cubesOn.addAll(newCells)
        } else {
            cubesOn.removeAll(newCells)
        }
    }
}
println("Cubes on: ${cubesOn.size}")

fun generateCubes(xStart:Int, xEnd:Int, yStart:Int, yEnd:Int, zStart:Int, zEnd:Int): List<Cubes> {
    val coordinates = mutableListOf<Cubes>()
    for (x in xStart..xEnd) {
        for (y in yStart..yEnd) {
            for (z in zStart..zEnd) {
                coordinates.add(Cubes(x,y,z))
            }
        }
    }
    return coordinates
}

data class Cubes(val x:Int, val y:Int, val z:Int)