/**
 * Day 10: Syntax Scoring
 */

val openToClose: Map<Char, Char> = mapOf('(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

fun main() {
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
                        // Error - add score and move onto next line
                        errorScore += illegalPoints[char]!!
                        break
                    }
                }
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
            var lineDiscarded = false
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
                        // Error - discard line and continue
                        lineDiscarded = true
                        break
                    }
                }
            }

            if (!lineDiscarded) {
                // Complete line and calculate score
                var completionScore: Long = 0

                for (char in closingStack.reversed()) {
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