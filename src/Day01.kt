/**
 * Sonar Sweep
 *
 * As the submarine drops below the surface of the ocean, it automatically performs a sonar sweep of
 * the nearby sea floor. On a small screen, the sonar sweep report (your puzzle input) appears: each
 * line is a measurement of the sea floor depth as the sweep looks further and further away from the
 * submarine.
 */

fun main() {
    fun part1(input: List<String>): Int {
        /**
         * Count the number of times a depth measurement increases from the previous
         * measurement. (There is no measurement before the first measurement.)
         */
        var increasedCount = 0
        var previousDepth: Int? = null

        for (i in input) {
            val currentDepth = i.toInt()
            if (previousDepth != null && currentDepth > previousDepth) {
                increasedCount += 1
            }
            previousDepth = currentDepth
        }
        return increasedCount
    }

    fun part2(input: List<String>): Int {
        /**
         * Count the number of times the sum of measurements in a three measurement sliding window
         * increases from the previous sum. Stop when there aren't enough measurements left to
         * create a new three-measurement sum.
         */
        var increasedCount = 0
        var previousDepthSum: Int? = null

        // Convert all input from strings to integers
        val intInput = input.map {it.toInt()}

        for (i in 0..intInput.size - 3) {
            val currentDepthSum = intInput.subList(i, i + 3).sum()
            if (previousDepthSum != null && currentDepthSum > previousDepthSum) {
                increasedCount += 1
            }
            previousDepthSum = currentDepthSum
        }

        return increasedCount
    }

    // Test against examples
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
