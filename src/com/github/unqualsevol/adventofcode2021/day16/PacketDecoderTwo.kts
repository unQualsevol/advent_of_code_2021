import java.io.File
import kotlin.math.min

val input = File("input").readText().map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
var position = 0

println("Result: ${readPackage()}")

fun readPackage(): Long {
    read(3)
    when (read(3)) {
        0L -> return processSubPackages().sum()
        1L -> return processSubPackages().reduce { acc, current -> acc * current }
        2L -> return processSubPackages().minOrNull()!!
        3L -> return processSubPackages().maxOrNull()!!
        4L -> return readLiteral()
        5L -> {
            val values = processSubPackages()
            return if (values[0] > values[1]) 1 else 0
        }
        6L -> {
            val values = processSubPackages()
            return if (values[0] < values[1]) 1 else 0
        }
        7L -> {
            val values = processSubPackages()
            return if (values[0] == values[1]) 1 else 0
        }
        else ->
            throw IllegalArgumentException("Invalid Operator.")
    }
}

fun processSubPackages(): List<Long> {
    val result = mutableListOf<Long>()
    when(read(1)){
        0L -> {
            val subPacketsLength = read(15)
            val endOfPackage = position + subPacketsLength
            var i = 1
            while (position < endOfPackage) {
                result.add(readPackage())
                i++
            }
        }
        1L -> {
            val numberOfSubPackets = read(11)
            for (i in 1..numberOfSubPackets) {
                result.add(readPackage())
            }
        }
    }
    return result
}

fun readLiteral(): Long {
    var literal = 0L
    do {
        val hasNext = read(1) == 1L
        literal = (literal shl 4) + read(4)
    } while (hasNext)
    return literal
}

fun read(bits:Int): Long {
    val start = position
    val end = min(position+bits, input.length)
    val value = input.substring(start, end).toLong(2)
    position += bits
    return value
}
