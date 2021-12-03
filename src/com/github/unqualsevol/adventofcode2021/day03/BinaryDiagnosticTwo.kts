import java.io.File

val mostCommon: (List<String>, List<String>)-> List<String> = { zeros, ones ->
    if (zeros.size > ones.size) zeros else ones
}

val lessCommon: (List<String>, List<String>)-> List<String> = { zeros, ones ->
    if (ones.size < zeros.size) ones else zeros
}

fun filter(position: Int, report: List<String>, operation: (List<String>, List<String>) -> List<String>): List<String> {
    if(report.size == 1) return report
    val ones = mutableListOf<String>()
    val zeros = mutableListOf<String>()
    report.forEach() { if(it[position] == '0') zeros.add(it) else ones.add(it) }
    return operation(zeros, ones)
}

val report = File("input").readLines()

var position = 0

var moreCommonBitCriteria = report
var lessCommonBitCriteria = report

while (moreCommonBitCriteria.size > 1 || lessCommonBitCriteria.size > 1) {
    moreCommonBitCriteria = filter(position, moreCommonBitCriteria, mostCommon)
    lessCommonBitCriteria = filter(position, lessCommonBitCriteria, lessCommon)
    position++
}

val oxygenGeneratorRating = moreCommonBitCriteria[0].toInt(2)
val co2ScrubberRating = lessCommonBitCriteria[0].toInt(2)

println("Oxigen generator rating: $oxygenGeneratorRating, CO2 scrubber rating: $co2ScrubberRating, Result: ${oxygenGeneratorRating*co2ScrubberRating}")