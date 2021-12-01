import java.io.File

var count = 0
var previousEntry = Int.MAX_VALUE

File("input").forEachLine {
    val currentEntry = it.toInt()
    if(previousEntry < currentEntry) count++
    previousEntry = currentEntry
}

println("The number of time a depth measurement increases is: $count")

//Alternative

val input = File("input").readLines().map { it.toInt() }
count = input.filterIndexed{ index, value -> index > 0 && input[index-1] < value}.size

println("The number of time a depth measurement increases is: $count")