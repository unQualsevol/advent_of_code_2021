import java.io.File

var input = File("input").readLines()

var maxMagnitude = 0
for (i in input.indices) {
    for (j in input.indices) {
        if(i == j) continue
        maxMagnitude = maxOf(maxMagnitude, magnitude(add(toTree(input[i]), toTree(input[j]))))
    }
}
println("Largest magnitude: $maxMagnitude")

fun magnitude(tree: NodeTree): Int {
    val left = 3 * if(tree.left!!.isSingle()) tree.left!!.value!! else magnitude(tree.left!!)
    val right = 2 * if(tree.right!!.isSingle()) tree.right!!.value!! else magnitude(tree.right!!)
    return left + right
}

fun add(numberA: NodeTree, numberB: NodeTree): NodeTree {
    val newTree = NodeTree(null, numberA, numberB)
    return reduce(newTree)
}

fun toTree(snailFishNumber:String): NodeTree {
    return readPair(snailFishNumber, 1).first
}

fun readPair(snailFishNumber:String, position:Int): Pair<NodeTree, Int> {
    var currentPosition = position
    val left: NodeTree?
    var right: NodeTree? = null
    if(snailFishNumber[currentPosition] == '[') {
        val firstPart = readPair(snailFishNumber, currentPosition + 1)
        left = firstPart.first
        currentPosition = firstPart.second
    }
    else {
        left = NodeTree(readNumber(snailFishNumber, currentPosition))
        currentPosition++
    }

    if(currentPosition < snailFishNumber.length && snailFishNumber[currentPosition] == ',') {
        //comma
        currentPosition++
        if (snailFishNumber[currentPosition] == '[') {
            val firstPart = readPair(snailFishNumber, currentPosition + 1)
            right = firstPart.first
            currentPosition = firstPart.second
        } else {
            right = NodeTree(readNumber(snailFishNumber, currentPosition))
            currentPosition++
        }
        //closing pair
        currentPosition++
    }
    return Pair(NodeTree(null, left, right), currentPosition)
}

fun readNumber(snailFishNumber:String, position:Int): Int {
    return snailFishNumber[position].code - 48
}

fun reduce(tree: NodeTree): NodeTree {
    do {
        val exploded = explode(tree)
        var split = false
        if(exploded) continue
        split = split(tree)
    } while (exploded || split)
    return tree
}

fun explode(tree:NodeTree): Boolean {
    return explodeDfs(tree, 0, 0, null, false).exploded
}

fun explodeDfs(tree: NodeTree, level:Int, offset:Int, previous:NodeTree?, exploded: Boolean): ExplodeResponse {
    val newOffset = 0
    if(tree.isSingle()) {
        if(offset > 0) {
            tree.value = tree.value!! + offset
        }
        return ExplodeResponse(tree, newOffset, false)
    }
    if(tree.isPair()){
        if(!exploded && level >=4) {
            val toLeft = tree.left!!.value!!
            val toRight = tree.right!!.value!!
            if (previous != null) {
                previous.value = previous.value!! + toLeft
            }
            tree.explode()
            return ExplodeResponse(previous, toRight, true)
        }
        if(offset > 0) {
            tree.left!!.value = tree.left!!.value!! + offset
        }
        return ExplodeResponse(tree.right, newOffset, exploded)
    }

    val leftResponse = explodeDfs(tree.left!!, level+1, offset, previous, exploded)
    val rightResponse = explodeDfs(tree.right!!, level+1, leftResponse.offset, leftResponse.lastSingle, exploded || leftResponse.exploded)
    return ExplodeResponse(rightResponse.lastSingle, rightResponse.offset, exploded || leftResponse.exploded || rightResponse.exploded)

}

fun split(tree:NodeTree): Boolean {
    if(tree.split()) return true
    if(tree.left != null && split(tree.left!!)) return true
    if(tree.right != null && split(tree.right!!)) return true
    return false
}

data class NodeTree(var value:Int?, var left: NodeTree? = null, var right:NodeTree? = null){
    override fun toString(): String {
        if(value != null) return "$value"
        return "[${left.toString()}${if(right!=null) ",${right.toString()}" else ""}]"
    }

    fun isPair(): Boolean = this.left?.value != null && this.right?.value != null
    fun isSingle(): Boolean = this.value != null
    fun explode(): Unit {
        this.value = 0
        this.left = null
        this.right = null

    }
    fun split(): Boolean {
        if(this.isSingle() && this.value!! > 9)
        {
            val half = this.value!!/2
            val leftover = this.value!!%2
            this.value = null
            this.left = NodeTree(half)
            this.right = NodeTree(half+leftover)
            return true
        }
        return false
    }
}

data class ExplodeResponse(val lastSingle: NodeTree?, val offset: Int, val exploded: Boolean)

