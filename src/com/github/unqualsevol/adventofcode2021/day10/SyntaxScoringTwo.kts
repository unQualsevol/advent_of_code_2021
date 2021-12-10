import java.io.File
import java.util.*

val openCloseMap = mapOf(Pair("(", ")"), Pair("[", "]"), Pair("{", "}"), Pair("<", ">"))
val scores = File("input").readLines().map {
    computeScore(missingClosers(it))
}.filter { it > 0 }.sorted()
val middleScore = scores[scores.size/2]

println("Middle score: $middleScore")

fun missingClosers(line: String): List<String> {
    val stack: Deque<String> = ArrayDeque()
    for (i in line.indices) {
        val current = line.substring(i,i+1)
        when {
            isOpen(current) -> stack.push(current)
            isClosing(stack.peekFirst(), current) -> stack.pollFirst()
            else -> return emptyList()
        }
    }
    return stack.map { openCloseMap[it]!! }
}

fun isOpen(current: String): Boolean {
    return openCloseMap.keys.contains(current)
}

fun isClosing(last: String?, current: String): Boolean = last != null && current == openCloseMap[last]

fun computeScore(missingClosers: List<String>): Long {
    if(missingClosers.isEmpty()) return 0
    return missingClosers.fold(0L){acc, current -> acc * 5 + (openCloseMap.values.indexOf(current)+1) }
}