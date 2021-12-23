import kotlin.math.abs
import kotlin.math.max

/**
 * Day 17: Trick Shot
 */

fun main() {

    fun parseInput(input: List<String>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val spaceSeparated = input.first().split(" ")
        val xValues = spaceSeparated[2].substring(2 until spaceSeparated[2].length - 1).split("..").map{ it.toInt() }
        val yValues = spaceSeparated[3].substring(2).split( "..").map{ it.toInt() }

        return Pair(Pair(xValues[0], xValues[1]), Pair(yValues[0], yValues[1]))
    }

    fun fireProbe(initialVelocity: Pair<Int, Int>, xRange:Pair<Int, Int>, yRange:Pair<Int, Int>): Int? {
        /**
         * Simulate the probe being fired at the initial velocity. Returns the maximum y position if the probe lands in
         * the target area, null otherwise.
         */
        var xPosition = 0
        var yPosition = 0
        var xVelocity = initialVelocity.first
        var yVelocity = initialVelocity.second

        var maxY = 0

        // Stop when probe is beyond target either horizontally or vertically
        while (yPosition > yRange.first && xPosition < xRange.second) {
            // Update probe position
            xPosition += xVelocity
            yPosition += yVelocity

            maxY = max(maxY, yPosition)

            // Check if probe is in target area
            if (xRange.first <= xPosition && xPosition <= xRange.second &&
                yRange.first <= yPosition && yPosition <= yRange.second) {
                return maxY
            }

            // Update probe velocity
            if (xVelocity > 0) {
                xVelocity -= 1
            } else if (xVelocity < 0) {
                xVelocity -= 1
            }

            yVelocity -= 1

        }

        return null
    }

    fun part1(input: List<String>): Int {
        /**
         * Return the y position of a trajectory that has the highest y position and still reaches the target area.
         */
        val (xRange, yRange) = parseInput(input)

        var maxY = 0

        // x component of initial velocity cannot be more than max X of target or else it will overshoot
        for (x in 1 .. xRange.second) {
            // Trying to maximise y position, so start at 0
            for (y in 0 .. abs(yRange.first)) {
                val yPos = fireProbe(Pair(x, y), xRange, yRange)

                if (yPos != null) {
                    maxY = max(maxY, yPos)
                }
            }
        }

        return maxY
    }

    fun part2(input: List<String>): Int {
        /**
         * Return the number of distinct initial velocities that cause the probe to be within the target area.
         */
        val (xRange, yRange) = parseInput(input)

        var targetHitCount = 0

        // x component of initial velocity cannot be more than max X of target or else it will overshoot
        for (x in 1 .. xRange.second) {
            for (y in yRange.first .. abs(yRange.first)) {
                if (fireProbe(Pair(x, y), xRange, yRange) != null) {
                    targetHitCount += 1
                }
            }
        }

        return targetHitCount
    }

    // Test against examples
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 45)
    check(part2(testInput) == 112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))

}
