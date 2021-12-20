import java.io.File

val input = File("input").readLines()
val imageEnhancementAlgorithm = input[0]
var image = input.subList(2, input.size)

image = enhance(expand(image, '.'), imageEnhancementAlgorithm, '.')
image = enhance(expand(image, imageEnhancementAlgorithm[0]), imageEnhancementAlgorithm, imageEnhancementAlgorithm[0])
print(image)
val litPixels = image.sumOf { line -> line.count{it == '#'} }
println(litPixels)

fun print(image: List<String>) {
    image.forEach { println(it) }
}

fun expand(image: List<String>, infinite: Char): List<String> {
    val newImage = mutableListOf<String>()
    newImage.add(CharArray(image[0].length+2){infinite}.joinToString(""))
    newImage.addAll(image.map { "$infinite$it$infinite" })
    newImage.add(CharArray(image[0].length+2){infinite}.joinToString(""))
    return newImage
}

fun enhance(image: List<String>, imageEnhancementAlgorithm: String, infinite: Char): List<String> {
    print(image)
    val newImage = mutableListOf<String>()
    for (i in image.indices) {
        val line = image[i]
        val newLine = CharArray(line.length){j -> imageEnhancementAlgorithm[adjacentCode(image, i, j, infinite)] }
        newImage.add(newLine.joinToString(""))
    }
    return newImage
}

fun adjacentCode(image: List<String>, y: Int, x:Int, infinite: Char): Int {
    var binaryString = ""
    for (i in -1..1) {
        for (j in -1..1) {
            val posY = y+i
            val posX = x+j
            if(posY < 0 || posY >= image.size || posX < 0 || posX >= image[0].length) {
                binaryString += if(infinite == '#') "1" else "0"
            } else {
                binaryString += if(image[posY][posX] == '#') "1" else "0"
            }
        }
    }
    return binaryString.toInt(2)
}