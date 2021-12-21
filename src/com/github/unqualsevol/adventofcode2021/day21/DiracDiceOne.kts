import java.io.File

var diceRolled = 1


val players = File("input").readLines().mapIndexed { index, it ->  Player(index, it[it.length-1].toString().toInt(), 0)}

 while (players.all { !it.win() }) {
     for (i in players.indices) {
         val current = players[i]
         current.calculateNewPosition(turnMovement(currentDice(diceRolled)))
         diceRolled += 3
         if(current.win()) break
     }
 }
println("Losing player score by the number of times the die rolled: ${players.find { !it.win() }!!.score * (diceRolled-1)}")

fun currentDice(dice: Int): Int {
    val current = dice % 100
    if(current == 0) return 100
    return dice % 100
}
fun turnMovement(dice: Int): Int = dice * 3 + 3

data class Player(val id: Int, var position: Int, var score:Int) {
    fun calculateNewPosition(movement: Int) {
        val newPosition = (position + movement) % 10
        position = if(newPosition == 0) 10 else newPosition
        score += position
    }

    fun win(): Boolean = score >= 1000

}
