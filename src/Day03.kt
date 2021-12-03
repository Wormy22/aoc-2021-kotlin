import kotlin.math.pow

/**
 * Day 3: Binary Diagnostic
 */


fun main() {


    fun part1(input: List<String>): Int {
        /**
         * Generate two binary numbers (called the gamma rate and the epsilon rate).
         * The power consumption can then be found by multiplying the gamma rate by the epsilon rate.
         *
         * Each bit in the gamma rate can be determined by finding the most common bit in the corresponding
         * position of all numbers in the diagnostic report.
         *
         * The epsilon rate is calculated in a similar way; rather than use the most common bit, the least common bit
         * from each position is used.
         */

        val numberSize = input[0].length
        var gammaBinary = ""

        // Determine most common bit for each position
        for (position in 0 until numberSize) {
            var ones = 0
            var zeroes = 0
            for (i in input) {
                if (i[position] == '1') {
                    ones += 1
                } else {
                    zeroes += 1
                }
            }

            if (ones > zeroes) {
                gammaBinary += "1"
            } else {
                gammaBinary += "0"
            }
        }

        // Calculate decimal value of gamma
        val gamma = gammaBinary.toInt(2)

        // Epsilon = NOT gamma, so can be calculated by subtracting gamma from maximum value of binary
        // number with same length (all 1's -> 2^n - 1)
        // e.g. If gamma were 0110 (= 6), epsilon = 1001 = ((2^4) - 1) - 6 = 9
        val epsilon = (2.0.pow(numberSize) - 1.0) - gamma

        return (epsilon * gamma).toInt()
    }

    fun part2(input: List<String>): Int {
        /**
         * Verify the life support rating, by multiplying the oxygen generator rating by the CO2 scrubber rating.
         *
         * To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position, and
         * keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 1 in the
         * position being considered.
         *
         * To find CO2 scrubber rating, determine the least common value (0 or 1) in the current bit position, and keep
         * only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 0 in the
         * position being considered.
         *
         * Repeat until only one number is left.
         */

        val numberSize = input[0].length

        // For each rating, create an array of indices pointing to elements in input that are still under consideration
        var oxygenIndices: Array<Int> = input.indices.map {i: Int ->  i}.toTypedArray()
        var co2Indices = oxygenIndices
        
        for (position in 0 until numberSize) {
            // Stop searching when only one element is left to consider
            if (oxygenIndices.size > 1) {
                // Split indices by whether they are pointing to a 1 or 0 at this position
                var zeroes: Array<Int> = emptyArray()
                var ones: Array<Int> = emptyArray()
                for (i in oxygenIndices) {
                    val bit = input[i][position]
                    if (bit == '1') {
                        ones += i
                    } else {
                        zeroes += i
                    }
                }

                // Only elements with most common digit are still under consideration
                if (ones.size >= zeroes.size) {
                    oxygenIndices = ones
                } else {
                    oxygenIndices = zeroes
                }
            }

            // Same process as for oxygen rating
            if (co2Indices.size > 1) {
                var zeroes: Array<Int> = emptyArray()
                var ones: Array<Int> = emptyArray()
                for (i in co2Indices) {
                    val bit = input[i][position]
                    if (bit == '1') {
                        ones += i
                    } else {
                        zeroes += i
                    }
                }

                // Only elements with least common digit are still under consideration
                if (zeroes.size <= ones.size) {
                    co2Indices = zeroes
                } else {
                    co2Indices = ones
                }
            }
        }

        assert(oxygenIndices.size == 1)
        assert(co2Indices.size == 1)

        val oxygenRating = input[oxygenIndices[0]].toInt(2)
        val co2Rating = input[co2Indices[0]].toInt(2)

        return oxygenRating * co2Rating
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
