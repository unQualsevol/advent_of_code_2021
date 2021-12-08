import java.io.File

var count = 0
val validLengths = listOf(2, 4, 3, 7)
File("input").forEachLine { line ->
    count += line.split("|")[1].trim().split(" ").count { validLengths.contains(it.length) }
}

println("Digits 1, 4, 7, or 8 appear $count times.")
