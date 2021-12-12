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
    when {
        current == "end" -> return 1
        isLowerCase(current) && path.contains(current) -> return 0
    }
    val newPath = path + listOf(current)
    val children = getConnectedCaves(current)
    return children.sumOf { dfs(it, newPath) }
}

println("Number of Paths: $count")