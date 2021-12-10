/**
 * Day 10: Syntax Scoring
 */

val openToClose: Map<Char, Char> = mapOf('(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

fun main() {

    fun checkLine(line: String): Pair<Char?, List<Char>?> {
        /**
         * Check if a line is valid i.e. if every opening character has a matching closing character in the correct
         * order
         */
        var closingStack: Array<Char> = emptyArray()

        for (char in line) {
            if (openToClose.containsKey(char)) {
                // Opening character - add corresponding closing to stack
                closingStack += openToClose[char]!!
            } else {
                // Closing character - check it is expected
                if (char == closingStack.last()) {
                    // Remove from stack
                    closingStack = closingStack.sliceArray(0 until closingStack.lastIndex)
                } else {
                    // Error - return illegal character
                    return Pair(char, null)
                }
            }
        }

        // Line is valid but incomplete - return list of closing characters in order they need to be added
        return Pair(null, closingStack.reversed())
    }


    fun part1(input: List<String>): Int {
        /**
         * Find the first illegal character in each corrupted line of the navigation subsystem and return the
         * total error score.
         */
        val illegalPoints = mapOf(')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )

        var errorScore = 0

        for (line in input) {
            val errorChar = checkLine(line).first

            if (errorChar != null) {
                errorScore += illegalPoints[errorChar]!!
            }
        }
        return errorScore
    }

    fun part2(input: List<String>): Long {
        /**
         * Find the completion string for each incomplete line, score the completion strings and return the middle
         * score.
         */

        val completionPoints = mapOf(')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4
        )

        var completionScores: Array<Long> = emptyArray()

        for (line in input) {
            val closingStack = checkLine(line).second

            if (closingStack != null) {
                // Complete line and calculate score
                var completionScore: Long = 0

                for (char in closingStack) {
                    completionScore *= 5
                    completionScore += completionPoints[char]!!
                }

                completionScores += completionScore
            }
        }

        // Return middle score when sorted
        return completionScores.sorted()[(completionScores.size - 1) / 2]
    }

    // Test against examples
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}