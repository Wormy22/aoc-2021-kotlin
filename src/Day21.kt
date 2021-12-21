import java.lang.Integer.min

fun main() {

    fun parseInput(input: List<String>): Pair<Int, Int> {
        /**
         * Return starting positions for each player
         */
        return Pair(input[0].split(": ")[1].toInt(),
                    input[1].split(": ")[1].toInt())
    }

    fun part1(input: List<String>): Int{
        /**
         * Play a practise game using the deterministic 100 sided dice.
         * Return the score of the losing player multiplied by the number of times the dice was rolled.
         */

        fun takeTurn(startPos: Int, startDice: Int): Pair<Int, Int>  {
            /**
             * Roll the dice three times and return the end position
             */
            var moveAmount = 0
            var endDice = startDice
            for (i in 1 .. 3) {
                endDice = (endDice % 100) + 1
                moveAmount += endDice
            }

            val endPos = ((startPos + moveAmount - 1) % 10) + 1

            return Pair(endPos, endDice)
        }

        var (playerOnePos, playerTwoPos) = parseInput(input)

        var lastDiceRoll = 0
        var diceRolls = 0
        var playerOneScore = 0
        var playerTwoScore = 0
        while (playerTwoScore < 1000) {
            var turnResult = takeTurn(playerOnePos, lastDiceRoll)
            playerOnePos = turnResult.first
            lastDiceRoll = turnResult.second
            playerOneScore += playerOnePos

            diceRolls += 3

            if (playerOneScore >= 1000) {
                break
            }

            turnResult = takeTurn(playerTwoPos, lastDiceRoll)
            playerTwoPos = turnResult.first
            lastDiceRoll = turnResult.second
            playerTwoScore += playerTwoPos
            diceRolls += 3
        }

        return min(playerOneScore, playerTwoScore) * diceRolls
    }

    fun part2(input: List<String>): Long {
        /**
         * Play the game with a Dirac die.
         * Return the number of universes in which the player with the most wins wins in.
         */
        var (playerOnePos, playerTwoPos) = parseInput(input)

        return 0
    }

    // Test against examples
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 739785)
    check(part2(testInput) == 444356092776315)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))

}