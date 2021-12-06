import java.io.File

var lanternFishCycles = File("input").readText().split(",").map { it.toInt() }
val originalSize = lanternFishCycles.size
val initializeIterations = 10
val speciesIncrementByDay = mutableListOf(originalSize.toLong())
for (i in 1..initializeIterations) {
    lanternFishCycles = lanternFishCycles.map { it-1 }
    val newBorns = lanternFishCycles.count { it < 0 }
    speciesIncrementByDay.add(newBorns.toLong())
    lanternFishCycles = lanternFishCycles.map { if (it < 0) 6 else it }
    (lanternFishCycles as MutableList<Int>).addAll(List(newBorns){8})
}
val iterations = 256
for (i in (initializeIterations+1)..iterations) {
    val newBorns = speciesIncrementByDay[i-7]+speciesIncrementByDay[i-9]
    speciesIncrementByDay.add(newBorns)
}
val total:Long = speciesIncrementByDay.sum()


println("Lanternfishes after 256 days: ${total}")