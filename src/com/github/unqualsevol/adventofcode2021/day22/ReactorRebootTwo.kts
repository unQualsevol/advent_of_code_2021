import java.io.File

val cuboids= mutableListOf<Cuboid>()

File("input").readLines().forEach { line ->
    val regex = """(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)""".toRegex()
    val matchResult = regex.find(line)
    val (onOff, x1, x2, y1, y2, z1, z2) = matchResult!!.destructured
    val newCuboid = Cuboid(IntRange(x1.toInt(), x2.toInt()),
        IntRange(y1.toInt(), y2.toInt()), IntRange(z1.toInt(), z2.toInt()), onOff)
    cuboids.add(newCuboid)
//
//    val intersectingCuboids = cuboids.filter { it.intersects(newCuboid)}
//
//    if(intersectingCuboids.isEmpty()) {
//        if(newCuboid.isOn()) {
//            cuboids.add(newCuboid)
//        }
//        return@forEach
//    }
//
//    if (newCuboid.isOff()) {
//        cuboids.removeAll(intersectingCuboids)
//        cuboids.addAll(intersectingCuboids.map { it.remove(newCuboid) }.flatten())
//        return@forEach
//    }
//
//    if(newCuboid.isOn()) {
//        val processQueue = mutableListOf(newCuboid)
//        while (processQueue.isNotEmpty()){
//            val current = processQueue.removeAt(0)
//            val overlapCuboid = intersectingCuboids.firstOrNull{it.intersects(current)}
//            if(overlapCuboid == null) {
//                cuboids.add(current)
//            } else {
//                processQueue.addAll(current.remove(overlapCuboid))
//            }
//        }
//        return@forEach
//    }
}

val reactorCore = mutableListOf<Pair<Int, Cuboid>>()
cuboids.forEach { cuboidToProcess ->
    val cuboidsToAddInCore = mutableListOf<Pair<Int, Cuboid>>()
    if(cuboidToProcess.isOn()) {
        cuboidsToAddInCore.add(Pair(1, cuboidToProcess))
    }

    reactorCore.forEach{ reactorCuboid ->
        val intersection = getIntersection(cuboidToProcess, reactorCuboid.second)
        if(intersection != null) {
            cuboidsToAddInCore.add(Pair(-reactorCuboid.first, intersection))
        }
    }

    reactorCore.addAll(cuboidsToAddInCore)
}

val answer = reactorCore.map { it -> it.first * it.second.getVolume() }.sum()
println("Answer: $answer")

fun getIntersection(A: Cuboid, B: Cuboid): Cuboid? {
    val intersection = Cuboid(
        IntRange(Math.max(A.x.start, B.x.start),
        Math.min(A.x.endInclusive, B.x.endInclusive)),
        IntRange(Math.max(A.y.start, B.y.start),
        Math.min(A.y.endInclusive, B.y.endInclusive)),
        IntRange(Math.max(A.z.start, B.z.start),
        Math.min(A.z.endInclusive, B.z.endInclusive)),
        "on"
    )
    if ((intersection.x.start > intersection.x.endInclusive) || (intersection.y.start > intersection.y.endInclusive) || (intersection.z.start > intersection.z.endInclusive)) {
        return null
    } else {
        return intersection
    }
}

data class Cuboid(val x: IntRange, val y: IntRange, val z: IntRange, val onOff: String) {
    fun isOn(): Boolean {
        return onOff == "on"
    }

    fun isOff(): Boolean {
        return onOff == "off"
    }

    fun getVolume(): Long {
        val lenght = x.endInclusive - x.start + 1L
        val width = y.endInclusive - y.start + 1L
        val height = z.endInclusive - z.start + 1L
        return lenght * width * height
    }

}
