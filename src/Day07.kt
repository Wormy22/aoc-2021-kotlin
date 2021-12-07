import kotlin.math.abs

/**
 * Day 7: The Treachery of Whales
 */
fun main() {

    fun calculateFuelUse(input: List<String>, constantBurn: Boolean = true): Int {
        /**
         * Given a list of the horizontal position of each crab, calculate the least amount of fuel required in order
         * for the horizontal position of all of the crabs to match.
         */
        val positions = input.first().split(",").map {it.toInt()}.sorted()

        // If fuel is burnt constantly, only need to check positions where a crab currently is
        val positionsToCheck: List<Int>
        if (constantBurn) {
            positionsToCheck = positions.distinct()
        } else {
            // Fuel use increases with each move - may use less fuel by moving to a position not occupied by a crab
            // Therefore check every position between minimum and maximum
            positionsToCheck = (positions.first() .. positions.last()).toList()
        }

        var minFuelUse: Int = Int.MAX_VALUE

        // Calculate fuel use for moving all crabs to every possible position
        for (position in positionsToCheck) {
            var fuelUse = 0

            for (p in positions) {
                // Find amount of fuel required to move crab to position
                if (constantBurn) {
                    // One fuel used for each move
                    fuelUse += abs(p - position)
                } else {
                    // Fuel use increases with each move
                    fuelUse += (1 .. abs(p - position)).sum()
                }
            }

            if (fuelUse < minFuelUse) {
                minFuelUse = fuelUse
            }
        }
        return minFuelUse
    }

    fun part1(input: List<String>): Int {
        return calculateFuelUse(input)
    }

    fun part2(input: List<String>): Int {
        return calculateFuelUse(input, false)
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}