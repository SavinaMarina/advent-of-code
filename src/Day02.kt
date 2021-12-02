fun main() {
    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        for (v in input) {
            val (direction, step) = v.split(" ")
            val stepValue = step.toInt()
            when (direction ){
                "forward" -> x += stepValue
                "down" -> y += stepValue
                "up" -> y -= stepValue
            }
        }
        return x * y
    }

    fun part1f(input: List<String>): Int {
        val (x, y) = input.map { val (direction, step) = it.split(" ")
                    val stepValue = step.toInt()
                    when (direction ){
                        "forward" -> Pair(stepValue, 0)
                        "down" -> Pair(0, stepValue)
                        "up" -> Pair(0, -stepValue)
                        else -> Pair(0,0)
                    }
        }.reduce { acc, v -> Pair(acc.first + v.first, acc.second + v.second) }
        return x * y
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var x = 0
        var y = 0
        for (v in input) {
            val (direction, step) = v.split(" ")
            val stepValue = step.toInt()
            when (direction ){
                "forward" -> {
                    x += stepValue
                    y += aim*stepValue
                }
                "down" -> aim += stepValue
                "up" -> aim -= stepValue
            }
        }
        return x * y
    }

    fun part2f(input: List<String>): Int {
        val (x, y, aim) = input.map { val (direction, step) = it.split(" ")
            val stepValue = step.toInt()
            when (direction ){
                "forward" -> Triple(stepValue,  0, 0)
                "down" -> Triple(0,  0, stepValue)
                "up" -> Triple(0,  0, -stepValue)
                else -> Triple(0, 0, 0)
            }
        }.reduce { acc, v ->
            Triple(acc.first + v.first, acc.second + v.first * acc.third, acc.third + v.third) }
        return x * y
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)
    check(part2f(testInput) == 900)

    val input = readInput("Day02")
    println(part1f(input))
    println(part2f(input))
}
