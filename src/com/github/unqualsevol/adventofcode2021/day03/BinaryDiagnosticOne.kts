import java.io.File

fun charNumericToInt(c: Char): Int {
    return c.code - 48
}

fun negateBinaryString(binaryString: String): String {
    return binaryString.map { c -> if(c == '0') '1' else '0' }.joinToString("")
}

fun toInt(value: String): Int {
    return value.toInt(2)
}

var count = 0
val map: MutableMap<Int, Int> = mutableMapOf()
File("input").forEachLine {
    it.forEachIndexed{ index, c ->
        map[index] = map.getOrDefault(index, 0)+ (charNumericToInt(c))
    }
    count++
}
val middle = count/2


val gammaBinary = map.map { if (it.value > middle) 1 else 0 }.joinToString("")
val gamma = toInt(gammaBinary)
val epsilon = toInt(negateBinaryString(gammaBinary))

println("Gamma rate: $gamma, Epsilon rate: $epsilon, Result: ${gamma*epsilon}")