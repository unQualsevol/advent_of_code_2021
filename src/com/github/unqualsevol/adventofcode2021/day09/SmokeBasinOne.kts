import java.io.File

val heightMap = File("input").readLines().map { line -> line.toCharArray().map { it.code - 48 } }
val maxY = heightMap.size
val maxX = heightMap[0].size

var riskLevel = 0

for (y in 0 until maxY) {
    for(x in 0 until maxX) {
        if(isLowPoint(y, x)) {
            riskLevel += 1+heightMap[y][x]
        }
    }
}
println("The sum of the risk levels of all points in the heightmap: $riskLevel")

fun isLowPoint(y: Int, x: Int): Boolean {
    val value = heightMap[y][x]
    val adjacent = listOf(safeValue(y-1, x), safeValue(y, x+1), safeValue(y+1, x), safeValue(y, x-1))
    return adjacent.all { value < it }
}

fun safeValue(y: Int, x: Int): Int {
    if(y < 0 || y >= maxY || x < 0 || x >= maxX) return 9
    return heightMap[y][x]
}
