import java.io.File

var count = 0
val windowSize = 3

val input = File("input").readLines().map { it.toInt() }
var previousValue = input.subList(0,windowSize).sum()
for (i in windowSize until input.size) {
    val currentValue = input.subList(i-2,i+1).sum()
    if(previousValue < currentValue) count++
    previousValue = currentValue
}

println("The number of time a depth measurement increases is: $count")

// Alternative

fun calculateWindow(input: List<Int>, index: Int):Int {
    return input.subList(index-2, index+1).sum();
}

count = input.filterIndexed{ index, _ -> index >= windowSize && calculateWindow(input, index-1) < calculateWindow(input, index)}.size
println("The number of time a depth measurement increases is: $count")
