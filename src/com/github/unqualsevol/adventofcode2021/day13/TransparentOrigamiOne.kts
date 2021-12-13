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
val maxX = coordinates.maxByOrNull { it.x }!!.x
val maxY = coordinates.maxByOrNull { it.y }!!.y

val firstFold = folds[0]
val count = if(firstFold.isUp()) foldUp(coordinates, firstFold.position, maxY).size else foldLeft(coordinates, firstFold.position, maxX).size
println("Dots visible after completing just the first fold: $count")

fun foldUp(coordinates: List<Coordinate>, foldRow:Int, maxY:Int): Set<Coordinate> {
    val unModifiedCoordinates = coordinates.filter { it.y < foldRow }
    val modifiedCoordinates = coordinates.filter { it.y > foldRow }.map { Coordinate(maxY-it.y, it.x) }
    return unModifiedCoordinates.union(modifiedCoordinates)
}

fun foldLeft(coordinates: List<Coordinate>, foldColumn:Int, maxX:Int): Set<Coordinate> {
    val unModifiedCoordinates = coordinates.filter { it.x < foldColumn }
    val modifiedCoordinates = coordinates.filter { it.x > foldColumn }.map { Coordinate(it.y, maxX-it.x) }
    return unModifiedCoordinates.union(modifiedCoordinates)
}

data class Coordinate(val y:Int, val x:Int)


data class Fold(val direction:String, val position:Int) {
    fun isUp(): Boolean = direction == "y"
}