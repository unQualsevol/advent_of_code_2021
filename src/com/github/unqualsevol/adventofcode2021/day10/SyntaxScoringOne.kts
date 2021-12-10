import java.io.File
import java.util.*

val openCloseMap = mapOf(Pair("(", ")"), Pair("[", "]"), Pair("{", "}"), Pair("<", ">"))
val totalSyntaxError = File("input").readLines().sumOf {
    calculateSyntaxError(it)
}

println("Total syntax error score: $totalSyntaxError")

fun calculateSyntaxError(line: String): Int {
    return computeScore(findIllegalCharacter(line))
}

fun findIllegalCharacter(line: String): String {
    val stack: Deque<String> = ArrayDeque()
    for (i in line.indices) {
        val current = line.substring(i,i+1)
        when {
            isOpen(current) -> stack.push(current)
            isClosing(stack.peekFirst(), current) -> stack.pollFirst()
            else -> return current
        }
    }
    return ""
}

fun isOpen(current: String): Boolean {
    return openCloseMap.keys.contains(current)
}

fun isClosing(last: String?, current: String): Boolean = last != null && current == openCloseMap[last]

fun computeScore(findIllegalCharacter: String) = when (findIllegalCharacter) {
    ")" -> 3
    "]" -> 57
    "}" -> 1197
    ">" -> 25137
    else -> 0
}