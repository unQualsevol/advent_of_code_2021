import java.io.File

var horizontal = 0
var depth = 0
File("input").forEachLine { line ->
    val regex = """(\w+)\s(\d)""".toRegex()
    val matchResult = regex.find(line)
    val (direction, valueString) = matchResult!!.destructured
    val value = valueString.toInt()
    when {
        "forward" == direction -> horizontal += value
        "down" == direction -> depth += value
        "up" == direction -> depth -= value
    }
}
println("Horizontal: $horizontal Depth: $depth Result: ${horizontal*depth}")