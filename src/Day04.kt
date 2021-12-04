import kotlin.math.sqrt

/**
 * Day 4: Giant Squid
 */

const val MARKED = -1

fun main() {

    var cardSize = 0

    fun parseInput(input: List<String>): Pair<List<Int>, Array<Array<Int>>> {
        /**
         * Load numbers and bingo cards from input.
         */
        var numbers: List<Int> = emptyList()
        var cards: Array<Array<Int>> = emptyArray()
        var card: Array<Int> = emptyArray()

        for (i in input.withIndex()) {
            if (i.index == 0) {
                // First row contains numbers to be drawn
                numbers = i.value.split(",").map{ it.toInt() }
            } else if (i.value == "") {
                // About to start a new card
                if (card.size > 1) {
                    cards += card
                    card = emptyArray()
                }
            } else {
                // Card row
                card += i.value.trim().split("\\s+".toRegex()).map{ it.toInt() }
            }
        }

        // Add last card
        cards += card

        // Store size of row/column for use later
        cardSize = sqrt(card.size.toDouble()).toInt()

        return Pair(numbers, cards)
    }

    fun checkCard(card: Array<Int>): Boolean {
        /**
         * Check if a card has a won by looking for a row or column that is fully marked.
         */
        for (i in 0 until cardSize) {
            // Check rows
            val rowStart = i * cardSize
            if (card.slice(rowStart until rowStart + cardSize).count{it == MARKED} == cardSize) {
                // Every number in row is marked, winning card
                return true
            }

           // Check columns
           if (card.filterIndexed() { index, _ -> index % cardSize == i}.count{it == MARKED} == cardSize) {
               // Every number in column is marked, winning card
               return true
           }
        }

        return false
    }

    fun calculateScore(card: Array<Int>, winningNumber: Int): Int {
        /**
         * Calculate the score for a winning card.
         */
        return winningNumber * (card.sum() + card.count { it == MARKED })
    }

    fun findWinningCard(input: List<String>, first: Boolean = true): Int {
        /**
         * Return the score for either the first or last winning board, depending on the value of "first"
         */
        var lastScore = 0
        val (numbers, cards) = parseInput(input)

        for (number in numbers.withIndex()) {
            for (cardIndex in cards.indices) {
                val card = cards[cardIndex]
                // Check if number in card
                val position = card.indexOf(number.value)

                if (position != -1) {
                    // Mark number by replacing it
                    card[position] = MARKED

                    // Only check for winning cards once 5 or more balls have been drawn
                    if (number.index >= 4) {
                        if (checkCard(card)) {
                            // Winning card, calculate score
                            lastScore =  calculateScore(card, number.value)

                            if (first) {
                                // Looking for first winning card, return now
                                return lastScore
                            } else {
                                // Looking for last winning card, empty card so it is not checked again
                                cards[cardIndex] = emptyArray()
                            }
                        }
                    }
                }
            }
        }
        return lastScore
    }

    fun part1(input: List<String>): Int {
        /**
         * Figure out which board will win first and return the score of the winning board, which is the sum of all
         * unmarked numbers on that board multiplied by the number that was just called when the board won.
         */
        return findWinningCard(input)
    }

    fun part2(input: List<String>): Int {
        /**
         * Figure out which board will win last and return the score of the winning board, which is the sum of all
         * unmarked numbers on that board multiplied by the number that was just called when the board won.
         */
         return findWinningCard(input, false)
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
