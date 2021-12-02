fun main() {
    fun part1(input: List<Int>): Int {
	    var prev = 0
        var cnt = 0
        for (v in input) {
            if (v > prev && prev != 0) cnt++
            prev = v
        }
        return cnt
    }
	
	fun part1f(input: List<Int>): Int {
		return input.windowed(2).count { (x,y) -> y > x}
	}

    fun part2(input: List<Int>): Int {
		var prevSum = 0
        var sum = 0
        var cnt = 0
        for (i in input.indices) {
            if (i >= 2)  {
                sum = input[i-2] + input[i-1] + input[i]
                if ((prevSum != 0) && (sum > prevSum)) {
                    cnt++
                }
                prevSum = sum
            }
        }
        return cnt
    }
	
	fun part2f(input: List<Int>): Int {
        return input.windowed(3) { it.sum() }
            .windowed(2).count { (x, y) -> y > x }
	}

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)

    val input = readInput("Day01").map { it.toInt() }
    println(part1f(input))
    println(part2f(input))
}
