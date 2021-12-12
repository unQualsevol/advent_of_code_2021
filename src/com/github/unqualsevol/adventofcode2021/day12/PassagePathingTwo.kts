import java.io.File

var caveConnections = File("input").readLines().map {
    val (start, end) = it.split("-")
    Pair(start, end)
}

val count = dfs("start", listOf())

fun isLowerCase(string:String): Boolean {
    return string.toCharArray().all { it.isLowerCase() }
}

fun getConnectedCaves(cave: String): List<String> {
    return caveConnections.filter { it.first == cave || it.second == cave }.map {
        if(it.first == cave) it.second else it.first
    }
}

fun dfs(current: String, path: List<String>): Int {
    val currentIsLowerCase = isLowerCase(current)
    when {
        current == "start" && path.isNotEmpty() -> return 0
        current == "end" -> return 1
        currentIsLowerCase && path.contains(current) && visitedSmallCaveTwice(path) -> return 0
    }
    val children = getConnectedCaves(current)
    val newPath = path + listOf(current)
    return children.sumOf { dfs(it, newPath) }
}

println("Number of Paths: $count")

fun PassagePathingTwo.visitedSmallCaveTwice(path: List<String>) =
    path.filter { isLowerCase(it) }.groupBy { it }.any { it.value.size > 1 }