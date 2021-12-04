class Board() {
    var values = mutableListOf<Pair<Int, Boolean>>()

    override fun toString(): String {
        return "values: $values"
    }
    private fun isWinningRow(row: Int) =
        values.filterIndexed { index, pair -> index in (row * 5.. row * 5 + 4) && pair.second }.count() == 5

    private fun isWinningColumn(column: Int): Boolean =
        values.filterIndexed { index, pair -> index % 5 == column && pair.second }.count() == 5

    fun mark(number: Int) {
        for ((index, p) in values.withIndex()) {
            if (p.first == number)
                values[index] = Pair(p.first, true)
        }
    }

    fun getUnmarkedSum(): Int = values.filter { !it.second }.sumOf { it.first }

    fun isWinner(): Boolean {
        for (i in 0..4)
            if (isWinningRow(i)) return true
        for (i in 0..4)
            if (isWinningColumn(i)) return true
        return false
    }
}

fun main() {


    fun getBingoList(input: List<String>) =
        input[0].split(",").map { it.toInt() }

    fun getBoardList(input: List<String>): List<Board> {
        val boardList = mutableListOf<Board>()
        var currentBoard = Board()
        var currentRow = 0
        var skipNextRow = false
        for ( (index, value) in input.withIndex()) {
            when {
                index <= 1 -> continue
                skipNextRow -> {
                    skipNextRow = false
                    continue
                }
                else -> {
                    currentBoard.values.addAll(value.trim().replace("  "," ").split(" ").map { Pair(it.trim().toInt(), false) })
                    currentRow++
                    if (currentRow == 5) {
                        boardList.add(currentBoard)
                        currentBoard = Board()
                        currentRow = 0
                        skipNextRow = true
                    }
                }
            }
        }
        return boardList
    }

    fun part1(input: List<String>): Int {
        val bingoList = getBingoList(input)
        val boardList = getBoardList(input)
        for (v in bingoList) {
           for (b in boardList) {
               b.mark(v)
               if (b.isWinner()) {
                   return v * b.getUnmarkedSum()
               }
           }
        }
        return 0
    }


    fun part2(input: List<String>): Int {
        val bingoList = getBingoList(input)
        val boardList = getBoardList(input)
        var lastBingo = 0
        var lastUnmarkedSum = 0
        val winnersList = mutableListOf<Board>()
        for (v in bingoList) {
            for (b in boardList) {
                b.mark(v)
                if (b.isWinner() && !winnersList.contains(b)) {
                    winnersList.add(b)
                    lastBingo = v
                    lastUnmarkedSum = b.getUnmarkedSum()
                }
            }
        }
        return lastBingo * lastUnmarkedSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
