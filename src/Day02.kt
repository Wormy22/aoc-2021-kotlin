/**
 * Dive!
 */

// Command constants
const val FORWARD = "forward"
const val DOWN = "down"
const val UP = "up"

fun main() {


    fun part1(input: List<String>): Int {
        /**
         * Submarine can take a series of commands
         *  - forward X increases the horizontal position by X units.
         *  - down X increases the depth by X units.
         *  - up X decreases the depth by X units.
         * Calculate the horizontal position and depth after following the planned course.
         * Returns final horizontal position multiplied by final depth.
         */
        var horizontalPosition = 0
        var depth = 0

        for (i in input) {
            val splitCommand = i.split(" ")
            val command = splitCommand[0]
            val amount = splitCommand[1].toInt()

            when (command) {
                FORWARD -> horizontalPosition += amount
                DOWN -> depth += amount
                UP -> depth -= amount
            }
        }
        return horizontalPosition * depth
    }

    fun part2(input: List<String>): Int {
        /**
         * Submarine can take a series of commands
         * - down X increases your aim by X units.
         * - up X decreases your aim by X units.
         * - forward X does two things:
         *     - It increases your horizontal position by X units.
         *     - It increases your depth by your aim multiplied by X.
         * Calculate the horizontal position and depth after following the planned course.
         * Returns final horizontal position multiplied by final depth.
         */
        var horizontalPosition = 0
        var depth = 0
        var aim = 0

        for (i in input) {
            val splitCommand = i.split(" ")
            val command = splitCommand[0]
            val amount = splitCommand[1].toInt()

            when (command) {
                FORWARD -> {
                    horizontalPosition += amount
                    depth += aim * amount
                }
                DOWN -> aim += amount
                UP -> aim -= amount
            }
        }
        return horizontalPosition * depth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
