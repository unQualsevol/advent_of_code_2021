import java.io.File

val movementFrequencyMap = mapOf(Pair(3, 1), Pair(4, 3), Pair(5, 6), Pair(6, 7), Pair(7, 6), Pair(8, 3), Pair(9, 1))
val players = File("input").readLines().mapIndexed { index, it ->  Player(index, it[it.length-1].toString().toInt(), 0)}

var gamesStates = mutableListOf(GameState(players[0], players[1], 1))

var player1Count = 0L
var player2Count = 0L
do {
    val newGameStates = mutableListOf<GameState>()
    for (gameState in gamesStates) {
        val afterPlayer1States = createPlayer1GameStates(gameState)
        player1Count += afterPlayer1States.filter { it.resolved() }.sumOf { it.quantity }
        for (state in afterPlayer1States.filter { !it.resolved() }) {
            val afterPlayer2States = createPlayer2GameStates(state)
            player2Count += afterPlayer1States.filter { it.resolved() }.sumOf { it.quantity }
            newGameStates.addAll(afterPlayer2States.filter { !it.resolved() })
        }

    }
    gamesStates = newGameStates

} while (gamesStates.isNotEmpty())
println("Times winner won: ${maxOf(player1Count, player2Count)}")

fun createPlayer1GameStates(gameState: GameState): List<GameState> {
    return movementFrequencyMap.map { GameState(gameState.player1.calculateNewPosition(it.key), gameState.player2, gameState.quantity*it.value) }
}

fun createPlayer2GameStates(gameState: GameState): List<GameState> {
    return movementFrequencyMap.map { GameState(gameState.player1, gameState.player2.calculateNewPosition(it.key), gameState.quantity*it.value) }
}

data class Player(val id: Int, var position: Int, var score:Int) {
    fun calculateNewPosition(movement: Int): Player {
        val add = (position + movement) % 10
        val newPosition = if(add == 0) 10 else add
        val newScore = score + newPosition
        return Player(id, newPosition, newScore)
    }

    fun win(): Boolean = score >= 21

}

data class GameState(val player1: Player, val player2:Player, val quantity: Long) {
    fun resolved() = player1.win() || player2.win()
}
