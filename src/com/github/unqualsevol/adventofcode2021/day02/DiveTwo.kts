import java.io.File

var horizontal = 0
var depth = 0
var aim = 0
File("input").forEachLine { line ->
    val regex = """(\w+)\s(\d)""".toRegex()
    val matchResult = regex.find(line)
    val (direction, valueString) = matchResult!!.destructured
    val value = valueString.toInt()
    when {
        "down" == direction -> aim += value
        "up" == direction -> aim -= value
        "forward" == direction -> {
            horizontal += value
            depth += aim * value
        }
    }
}
println("Horizontal: $horizontal Depth: $depth Result: ${horizontal*depth}")