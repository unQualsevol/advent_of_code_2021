import java.io.File

var count = 0
File("input").forEachLine {line ->
    val entry = line.split("|").map { it.trim().split(" ") }
    val signalPattern = entry[0]
    val mapping = buildMapping(signalPattern)
    val outputValue = entry[1]
    count += decode(outputValue, mapping)
}

println("Sum of all the output values: $count")

fun buildMapping(signalPattern: List<String>): List<String> {
    val one = signalPattern.find { word -> word.length == 2 }!!
    val four = signalPattern.find { word -> word.length == 4 }!!
    val seven = signalPattern.find { word -> word.length == 3 }!!
    val eight = signalPattern.find { word -> word.length == 7 }!!
    val two = signalPattern.find { word -> word.length == 5 && four.filter { word.contains(it) }.length == 2 }!!
    val three = signalPattern.find { word -> word.length == 5 && word.filterNot { seven.contains(it) }.length == 2 }!!
    val five = signalPattern.find { word -> word.length == 5 && word != two && word != three }!!
    val six = signalPattern.find { word -> word.length == 6 && one.filter { word.contains(it) }.length == 1 }!!
    val nine = signalPattern.find { word -> word.length == 6 && word.filterNot { four.contains(it) }.length == 2 }!!
    val zero = signalPattern.find { word -> word.length == 6 && word != six && word != nine}!!

    return listOf(zero, one, two, three, four, five, six, seven, eight, nine)
}

fun decode(output: List<String>, mapping: List<String>): Int {

    return output.map { number ->
        mapping.indexOfFirst { it.length == number.length && it.filterNot { char -> number.contains(char) }.isEmpty() }
    }.joinToString("").toInt()
}

