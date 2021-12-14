import java.io.File
import java.util.*

run(10)
run(40)

fun run(steps: Int) {

    val polymerTemplateRegex = """^\w+$""".toRegex()
    val pairInsertionRulesRegex = """^(\w+) -> (\w)$""".toRegex()

    var polymerTemplate: String = ""
    val pairInsertionRules = mutableMapOf<String, String>()
    File("input").forEachLine { line ->
        when {
            line.matches(polymerTemplateRegex) -> polymerTemplate = line
            line.matches(pairInsertionRulesRegex) -> {
                val matchResult = pairInsertionRulesRegex.find(line)
                val (pair, insertion) = matchResult!!.destructured
                pairInsertionRules[pair] = insertion
            }
        }
    }
    val polymerComponents = polymerTemplate.toCharArray().toList()
    val frequencies = polymerComponents.distinct().associateWith { Collections.frequency(polymerComponents, it).toLong() }.toMutableMap()

    var pairsFrequency = mutableMapOf<String, Long>()
    for (i in 1 until polymerTemplate.length) {
        val current = polymerTemplate.slice(i - 1..i)
        val f = pairsFrequency.getOrDefault(current, 0L)
        pairsFrequency[current] = f+1
    }

    for (i in 1..steps) {
        val newPairFrequency = mutableMapOf<String, Long>()
        pairsFrequency.forEach { entry ->
            val pair = entry.key
            val pairFrequency = entry.value

            val newChar = pairInsertionRules[pair]!!
            val charFrequency = frequencies.getOrDefault(newChar[0], 0L)
            frequencies[newChar[0]] = charFrequency + pairFrequency

            val newFirstPair = pair[0] + newChar
            val firstFrequency = newPairFrequency.getOrDefault(newFirstPair, 0L)
            newPairFrequency[newFirstPair] = firstFrequency + pairFrequency

            val newSecondPair = newChar + pair[1]
            val secondFrequency = newPairFrequency.getOrDefault(newSecondPair, 0L)
            newPairFrequency[newSecondPair] = secondFrequency + pairFrequency
        }
        pairsFrequency = newPairFrequency
    }
    val max = frequencies.values.maxOrNull()!!
    val min = frequencies.values.minOrNull()!!

    println("After $steps steps, the quantity of the most common element and subtract the quantity of the least common element is: ${max - min}")
}
