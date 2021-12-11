/**
 * Day 11: Dumbo Octopus
 */

class Octopus(private var energy: Int) {
    /**
     * Class representing a bioluminescent octopus
     */
    private var neighbours: MutableSet<Octopus> = mutableSetOf()
    private var flashed: Boolean = false

    fun addNeighbour(neighbour: Octopus) {
        neighbours.add(neighbour)
    }

    fun increaseEnergy(checkFlash: Boolean = false): Int {
        energy += 1

        if (checkFlash) {
            return checkFlash()
        }

        return 0
    }

    fun checkFlash(): Int {
        var flashCount = 0

        if (!flashed && energy > 9) {
            // Flash - increase energy level of all adjacent octopuses
            flashed = true
            flashCount = 1
            for (neighbour in neighbours) {
                flashCount += neighbour.increaseEnergy(true)
            }
        }

        return flashCount
    }

    fun resetFlashStatus() {
        if (flashed) {
            energy = 0
            flashed = false
        }
    }

}

fun main() {

    fun loadCavern(input: List<String>): Array<Octopus>  {
        /**
         * Parse input to obtain an array of Octopuses.
         */
        var cavern: Array<Octopus> = emptyArray()

        for (row in input.withIndex()) {
            for (octopus in row.value.withIndex()) {
                val newOctopus = Octopus(Character.getNumericValue(octopus.value))

                // Add left neighbour if not first octopus in row
                if (octopus.index > 0) {
                    cavern.last().addNeighbour(newOctopus)
                    newOctopus.addNeighbour(cavern.last())
                }

                // Add top neighbours if not first row
                if (row.index > 0) {
                    val prevOctopusIndex = cavern.lastIndex - row.value.length + 1
                    cavern[prevOctopusIndex].addNeighbour(newOctopus)
                    newOctopus.addNeighbour(cavern[prevOctopusIndex])

                    if (octopus.index > 0) {
                        // Diagonal top left
                        cavern[prevOctopusIndex - 1].addNeighbour(newOctopus)
                        newOctopus.addNeighbour(cavern[prevOctopusIndex - 1])
                    }

                    if (octopus.index < row.value.lastIndex) {
                        // Diagonal top right
                        cavern[prevOctopusIndex + 1].addNeighbour(newOctopus)
                        newOctopus.addNeighbour(cavern[prevOctopusIndex + 1])
                    }
                }

                cavern += newOctopus
            }
        }

        return cavern
    }

    fun simulateStep(cavern: Array<Octopus>): Int {
        /**
         * Simulate a single step and return the number of flashes
         */
        var flashCount = 0
        // Increase energy
        for (octopus in cavern) {
            octopus.increaseEnergy()
        }

        // Check for flashes
        for (octopus in cavern) {
            flashCount += octopus.checkFlash()
        }

        // Reset
        for (octopus in cavern) {
            octopus.resetFlashStatus()
        }

        return flashCount
    }

    fun part1(input: List<String>): Int {
        /**
         * Return number of total flashes after 100 steps
         */
        val cavern = loadCavern(input)

        var flashCount = 0

        for (step in 1 .. 100) {
            flashCount += simulateStep(cavern)
        }

        return flashCount
    }

    fun part2(input: List<String>): Int {
        /**
         * Return first step where all octopuses flash at the same time
         */
        val cavern = loadCavern(input)

        var flashCount = 0
        var step = 0
        while (flashCount < cavern.size) {
            step += 1
            flashCount = simulateStep(cavern)
        }

        return step
    }

    // Test against examples
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))

}