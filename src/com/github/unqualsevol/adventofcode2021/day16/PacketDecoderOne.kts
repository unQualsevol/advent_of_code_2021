import java.io.File
import kotlin.math.min

val input = File("input").readText().map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
var position = 0
var totalVersion = 0
readPackage()
println("Sum of all version packets: $totalVersion")

fun readPackage() {
    val version = read(3)
    totalVersion += version
    val operator = read(3)
    when(operator) {
        4 -> {
            do {
                val hasNext = read(1) == 1
                read(4)
            } while (hasNext)
            return
        }
        else -> {
            when(read(1)){
                0 -> {
                    val subPacketsLength = read(15)
                    val endOfPackage = position + subPacketsLength
                    var i = 1
                    while (position < endOfPackage) {
                        readPackage()
                        i++
                    }
                }
                1 -> {
                    val numberOfSubPackets = read(11)
                    for (i in 1..numberOfSubPackets) {
                        readPackage()
                    }
                }
            }
        }
    }
}

fun read(bits:Int): Int {
    val start = position
    val end = min(position+bits, input.length)
    val value = input.substring(start, end).toInt(2)
    position += bits
    return value
}
