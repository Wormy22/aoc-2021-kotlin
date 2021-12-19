import java.util.Collections.max
import java.util.Collections.min

/**
 * Day 14: Extended Polymerization
 */

fun main() {

    fun parseInput(input: List<String>): Pair<String, Map<String, String>> {
        /**
         * Extract polymer and insertion rules from input
         */
        var polymerTemplate = ""
        val insertionRules: MutableMap<String, String> = mutableMapOf()

        for (line in input.withIndex()) {
            if (line.index == 0) {
                // Polymer template
                polymerTemplate = line.value
            } else if (line.index >= 2) {
                // Pair insertion rule
                val (pair, replacement) = line.value.split(" -> ")
                insertionRules[pair] = replacement
            }
        }

        return Pair(polymerTemplate, insertionRules)
    }

    fun pairInsertion(polymerTemplate: String, insertionRules: Map<String, String>): String {
        /**
         * Perform pair insertion on polymer
         */
        var newPolymerTemplate = ""

        for (i in 0 until polymerTemplate.lastIndex) {
            val firstChar = polymerTemplate[i]
            val secondChar = polymerTemplate[i + 1]
            val pair: String = firstChar.toString() + secondChar.toString()

            if (insertionRules.contains(pair)) {
                newPolymerTemplate += firstChar + insertionRules[pair]!!
            }
        }

        // Add final char
        newPolymerTemplate += polymerTemplate.last()

        return newPolymerTemplate
    }

    fun pairInsertionLoop(input: List<String>, steps: Int): Long {
        /**
         * Apply the specified number of pair insertions to the polymer template and find the most and least common
         * elements in the result. Return the quantity of the most common element minus the quantity of the least
         * common.
         */
        var (polymerTemplate, insertionRules) = parseInput(input)

        // Perform pair insertion
        for (i in 1 .. steps) {
            polymerTemplate = pairInsertion(polymerTemplate, insertionRules)
        }

        // Find most and least common elements
        val characterCounts = polymerTemplate.associateBy { char -> polymerTemplate.count { it == char } }

        return max(characterCounts.keys).toLong() - min(characterCounts.keys).toLong()
    }

    fun part1(input: List<String>): Long {
        return pairInsertionLoop(input, 10)
    }

    fun part2(input: List<String>): Long {
        return pairInsertionLoop(input, 40)
    }

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))

}