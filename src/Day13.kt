/**
 * Day 13: Transparent Origami
 */

fun main() {

    fun parseInput(input: List<String>): Pair<Array<Array<Boolean>>, Array<Pair<String, Int>>> {
        val paper: Array<Array<Boolean>>
        var folds: Array<Pair<String, Int>> = emptyArray()

        var dots = emptyArray<Pair<Int, Int>>()
        var maxX = 0
        var maxY = 0

        for (line in input) {
            if (line.contains(",")) {
                // Dot location
                val coords = line.split(",").map { it.toInt() }
                dots += coords.zipWithNext()

            } else if (line.contains("=")) {
                // Fold instruction
                val fold = line.split(" ")[2].split("=")
                val foldAxis: String = fold[0]
                val foldIndex = fold[1].toInt()

                // Ensure paper is sized to accomodate folds at the halfway point
                if (maxX == 0 && foldAxis == "x") {
                    maxX = (foldIndex * 2) + 1
                } else if (maxY == 0 && foldAxis == "y") {
                    maxY = (foldIndex * 2) + 1
                }

                folds += Pair(foldAxis, foldIndex)
            }
        }

        // Create paper of correct size and mark dots
        paper = Array(maxY ) {Array(maxX) { false } }
        for (dot in dots) {
            paper[dot.second][dot.first] = true
        }

        return Pair(paper, folds)
    }

    fun foldPaper(paper: Array<Array<Boolean>>, foldAxis: String, foldIndex: Int): Array<Array<Boolean>>{
        /**
         * Fold the paper along the specified line
         */
        val endXIndex: Int
        val endYIndex: Int
        val maxXIndex = paper.first().lastIndex
        val maxYIndex = paper.lastIndex

        if (foldAxis == "x") {
            endXIndex = foldIndex - 1
            endYIndex = maxYIndex
        } else {
            endXIndex = maxXIndex
            endYIndex = foldIndex - 1
        }

        // Create new paper with correct size
        val foldedPaper = Array(endYIndex + 1) {Array(endXIndex + 1) { false } }

        for (y in 0 .. endYIndex) {
            for (x in 0 .. endXIndex) {
                val foldY: Int
                val foldX: Int
                if (foldAxis == "x") {
                    foldX = maxXIndex - x
                    foldY = y
                } else {
                    foldX = x
                    foldY = maxYIndex - y
                }

                // Check if there is a dot on either side of the fold
                if (paper[y][x] or paper[foldY][foldX]){
                    foldedPaper[y][x] = true
                }
            }
        }

        return foldedPaper
    }

    fun countDots(paper: Array<Array<Boolean>>): Int {
        /**
         * Return the number of visible dots in the paper
         */
        var dotCount = 0

        paper.forEach { line -> dotCount += line.count { it } }

        return dotCount
    }

    fun outputPaper(paper: Array<Array<Boolean>>) {
        /**
         * Outputs the paper to the console
         */
        for (y in 0 .. paper.lastIndex) {
            var row = ""
            for (x in 0 .. paper.first().lastIndex) {
                if (paper[y][x]) {
                    row += "█"
                } else {
                    row += "."
                }
            }
            println(row)
        }
        println("---")
    }

    fun part1(input: List<String>): Int {
        /**
         * Return number of visible dots after first fold
         */
        var (paper, folds) = parseInput(input)

        val (axis, line) = folds[0]

        paper = foldPaper(paper, axis, line)

        return countDots(paper)
    }

    fun part2(input: List<String>){
        /**
         * Print the code obtained by folding the paper
         */
        var (paper, folds) = parseInput(input)

        for (fold in folds) {
            paper = foldPaper(paper, fold.first, fold.second)
        }

        outputPaper(paper)
    }

    // Test against examples
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)

}
