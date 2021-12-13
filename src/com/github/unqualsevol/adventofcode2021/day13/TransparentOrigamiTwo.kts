import java.io.File


val coordinateRegex = """(\d+),(\d+)""".toRegex()
val foldRegex = """fold along (\w)=(\d+)""".toRegex()
val coordinates = mutableListOf<Coordinate>()
val folds = mutableListOf<Fold>()
File("input").forEachLine { line ->
    if(line.matches(coordinateRegex)) {
        val matchResult = coordinateRegex.find(line)
        val (x, y) = matchResult!!.destructured
        coordinates.add(Coordinate(y.toInt(), x.toInt()))
    }
    if(line.matches(foldRegex)) {
        val matchResult = foldRegex.find(line)
        val (direction, position) = matchResult!!.destructured
        folds.add(Fold(direction, position.toInt()))
    }
}
var newCoordinates = coordinates.toSet()
var maxX = newCoordinates.maxByOrNull { it.x }!!.x
var maxY = newCoordinates.maxByOrNull { it.y }!!.y

for(fold in folds) {
    if (fold.isUp()) {
        newCoordinates = foldUp(newCoordinates, fold.position, maxY)
        maxY = fold.position - 1
    } else {
        newCoordinates = foldLeft(newCoordinates, fold.position, maxX)
        maxX = fold.position - 1
    }
}

for (i in 0..maxY) {
    for (j in 0..maxX) {
        if (newCoordinates.contains(Coordinate(i, j))) print("#") else print(".")
    }
    println()
}

fun foldUp(coordinates: Set<Coordinate>, foldRow:Int, maxY:Int): Set<Coordinate> {
    val unModifiedCoordinates = coordinates.filter { it.y < foldRow }
    val modifiedCoordinates = coordinates.filter { it.y > foldRow }.map { Coordinate(maxY-it.y, it.x) }
    return unModifiedCoordinates.union(modifiedCoordinates)
}

fun foldLeft(coordinates: Set<Coordinate>, foldColumn:Int, maxX:Int): Set<Coordinate> {
    val unModifiedCoordinates = coordinates.filter { it.x < foldColumn }
    val modifiedCoordinates = coordinates.filter { it.x > foldColumn }.map { Coordinate(it.y, maxX-it.x) }
    return unModifiedCoordinates.union(modifiedCoordinates)
}

data class Coordinate(val y:Int, val x:Int)


data class Fold(val direction:String, val position:Int) {
    fun isUp(): Boolean = direction == "y"
}