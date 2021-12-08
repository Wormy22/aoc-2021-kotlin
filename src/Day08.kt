/**
 * Day 8: Seven Segment Search
 */

fun main() {

    fun parseInput(input: List<String>): Pair<Array<List<String>>, Array<List<String>>> {
        var signals: Array<List<String>> = emptyArray()
        var digits: Array<List<String>> = emptyArray()

        for (line in input) {
            val (signalsCombined, digitsCombined) = line.split(" | ")
            // TODO - sort within strings?
            // TODO - combine parse and processing to avoid having to loop over twice
            signals += signalsCombined.split(" ")
            digits += digitsCombined.split(" ")
        }
        return Pair(signals, digits)
    }

    fun part1(input: List<String>): Int {
        val (_, digitsArray) = parseInput(input)

        val uniqueLengths = mapOf(
            2 to 1,
            4 to 4,
            3 to 7,
            7 to 8
        )

        var counts = IntArray(9)

        for (digits in digitsArray) {
            for (digit in digits) {
                if (digit.length in uniqueLengths) {
                    counts[uniqueLengths[digit.length]!!] += 1
                }
            }
        }

        return counts.sum()
    }

    fun part2(input: List<String>): Int {
        val (signalsArray, digitsArray) = parseInput(input)
        val numberToSegments = arrayOf("abcefg",
            "cf",
            "acdeg",
            "acdfg",
            "bcdf",
            "abdfg",
            "abdfeg",
            "acf",
            "abcdefg",
            "abcdfg"
        )

        return 0
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))

}