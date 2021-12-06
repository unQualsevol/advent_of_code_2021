import java.io.File

var lanternFishCycles = File("input").readText().split(",").map { it.toInt() }
val iterations = 80
for (i in 1..iterations) {
    lanternFishCycles = lanternFishCycles.map { it-1 }
    val newBorns = lanternFishCycles.count { it < 0 }
    lanternFishCycles = lanternFishCycles.map { if (it < 0) 6 else it }
    (lanternFishCycles as MutableList<Int>).addAll(List(newBorns){8})
}
println("Lanternfishes after 80 days: ${lanternFishCycles.size}")