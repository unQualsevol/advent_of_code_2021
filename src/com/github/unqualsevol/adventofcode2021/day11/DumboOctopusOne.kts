import java.io.File

val steps = 100
var count = 0
val octopuses = File("input").readLines().map { line -> line.toCharArray().map { it.code - 48 }.toMutableList() }.toMutableList()

for (step in 1..steps) {
    val willFlash = mutableSetOf<Coordinate>()
    val flashed = mutableSetOf<Coordinate>()
    octopuses.forEachIndexed { y, octopusLine ->
        octopusLine.forEachIndexed { x, octopus ->
            val increasedOctopus = octopus + 1
            if(increasedOctopus > 9) {
                willFlash.add(Coordinate(y, x))
            }
            octopuses[y][x] = increasedOctopus
        }
    }

    while (willFlash.isNotEmpty()) {
        flashed.addAll(willFlash)
        val propagatedFlash = willFlash.map {
            val adjacentWillFlash = mutableListOf<Coordinate>()
            val (y, x) = it
            for (i in -1..+1) {
                for (j in -1..+1) {
                    val adjacentY = y + i
                    val adjacentX = x + j
                    if (i == 0 && j == 0 || adjacentY !in 0..9 || adjacentX !in 0..9) continue
                    val increasedOctopus = ++octopuses[adjacentY][adjacentX]
                    val coordinate = Coordinate(adjacentY, adjacentX)
                    if(increasedOctopus > 9 && !flashed.contains(coordinate)) {
                        adjacentWillFlash.add(coordinate)
                    }
                }
            }
            adjacentWillFlash
        }.flatten()
        willFlash.clear()
        willFlash.addAll(propagatedFlash)
    }

    flashed.forEach { octopuses[it.y][it.x] = 0 }
    count += flashed.size
}
println("Total flashes after 100 steps: $count")


data class Coordinate(val y:Int, val x:Int)