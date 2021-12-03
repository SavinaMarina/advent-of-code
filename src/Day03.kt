enum class FilteringMethod {
    MOST_COMMON, LEAST_COMMON
}

fun main() {

    fun part1(input: List<String>): Int {
        val list = mutableListOf<Map<Char, Int>>()
        for (i in input[0].indices)
            list.add(input.groupingBy { it[i] }.eachCount())
        val gammaRate = list.joinToString("")
            { if (it.getOrDefault('0', 0) > it.getOrDefault('1', 0)) "0" else "1" }.toInt(2)
        val total = "1".repeat(input[0].length).toInt(2)
        return gammaRate * (total - gammaRate)
    }

    fun part1f(input: List<String>): Int {
        val gammaRate = input.fold (List(input[0].length) {0}) { acc, it ->
            val res = mutableListOf<Int>()
            for (i in it.indices) {
                res.add(if (it[i].digitToInt() == 1) {
                    acc[i] + it[i].digitToInt()
                } else
                    acc[i])
            }
            res
        }.map { if (it > input.size - it) '1' else '0' }.joinToString("").toInt(2)
        val total = "1".repeat(input[0].length).toInt(2)
        return (total - gammaRate) * gammaRate
    }

    fun filterByPositionRecursively(input:List<String>, position:Int, filteringMethod: FilteringMethod): List<String> {
        val totalCount = input.size
        var countOf1 = 0
        for (v in input) {
            countOf1 += v[position].digitToInt()
        }
        val countOf0 = totalCount - countOf1
        val filteringValue = if (filteringMethod == FilteringMethod.MOST_COMMON) {
            when {
                countOf1 > countOf0 -> 1
                countOf1 == countOf0 -> 1
                else -> 0
            }
        } else {
            when {
                countOf1 < countOf0 -> 1
                countOf1 == countOf0 -> 0
                else -> 0
            }
        }
        val filteredList = input.filter { it[position].digitToInt() == filteringValue }
        return if ((position == input[0].length - 1) || (filteredList.size == 1))
            filteredList
        else
            filterByPositionRecursively(filteredList, position + 1, filteringMethod)
    }

    fun part2(input: List<String>): Int {
        val oxygenGeneratorRating =  filterByPositionRecursively(input,0, FilteringMethod.MOST_COMMON)
            .joinToString( "" ).toInt(2)
        val cO2ScrubberRating =  filterByPositionRecursively(input,0, FilteringMethod.LEAST_COMMON)
            .joinToString( "" ).toInt(2)
        return oxygenGeneratorRating * cO2ScrubberRating
    }

    fun part2f(input: List<String>): Int {
        return 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part1f(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part1f(input))
    println(part2(input))
}
