import java.io.File


val input = File("input").readLines()

val numbers = input[0].split(",").map { it.toInt() }

var boardLines = mutableListOf<String>()
val boards = mutableListOf<Board>()

for (line in input.subList(2, input.size)) {
    if(line == "") {
        boards.add(createBoard(boardLines))
        boardLines = mutableListOf()
        continue
    }
    boardLines.add(line)
}

var bingo = false
var i = 0

while (boards.size>1) {
    val calledNumber = numbers[i]

    boards.forEach {
        it.checkNumber(calledNumber)
    }

    boards.removeIf { it.hasBingo() }

    i++
}
val lastBoard = boards[0]
while (!lastBoard.hasBingo()){
    val calledNumber = numbers[i]
    lastBoard.checkNumber(calledNumber)
    i++;
}
println("Board score: ${boards[0].getScore(numbers[i-1])}")

fun createBoard(boardLines: List<String>): Board {
    val horizontalLines = boardLines.map {
            line -> line.trim().split("\\s+".toRegex()).map {
            value -> Cell(value.toInt()) } }
    val verticalLines = mutableListOf<List<Cell>>()
    for (i in horizontalLines.indices){
        val verticalLine = mutableListOf<Cell>()
        for (j in horizontalLines.indices) {
            verticalLine.add(horizontalLines[j][i])
        }
        verticalLines.add(verticalLine)
    }

    return Board(horizontalLines, verticalLines)
}

data class Board(val horizontalLines: List<List<Cell>>, val verticalLines: MutableList<List<Cell>>) {
    fun checkNumber(value: Int): Boolean {
        val cell = horizontalLines.flatten().firstOrNull { it.number == value }
        cell?.checked = true
        return cell != null
    }

    fun hasBingo(): Boolean {
        return hasBingo(horizontalLines) || hasBingo(verticalLines)
    }

    private fun hasBingo(lines: List<List<Cell>>) : Boolean{
        return lines.any { line -> line.all { it.checked } }
    }

    fun getScore(calledNumber: Int): Int {
        val cells = horizontalLines.flatten()
        val unCheckedScore = cells.filter { !it.checked }.map { it.number }.sum()
        return unCheckedScore * calledNumber
    }

    fun printBoard() {
        println("Horizontal")
        for (i in horizontalLines.indices){
            for (j in horizontalLines.indices) {
                print("${horizontalLines[i][j]} ")
            }
            println();
        }
        println("Vertical")
        for (i in horizontalLines.indices){
            for (j in horizontalLines.indices) {
                print("${verticalLines[i][j]} ")
            }
            println();
        }
    }
}

data class Cell(val number: Int, var checked: Boolean = false)