import java.util.Collections.min

/**
 * Day 9: Smoke Basin
 */

class Point(private val height: Int) {
    /**
     * Class representing a point in the cave.
     */
    private var neighbours: MutableSet<Point> = mutableSetOf()
    private var basinVisited = false

    fun addNeighbour(neighbour: Point) {
        neighbours.add(neighbour)
    }

    fun isLowPoint(): Boolean {
        val minNeighbourHeight = min(neighbours.map { it.height })
        return height < minNeighbourHeight
    }

    fun riskFactor(): Int {
        return height + 1
    }

    fun calculateBasinSize(): Int {
        /**
         * Traverse neighbours to calculate basin size.
         */
        var basinSize = 1

        for (neighbour in neighbours) {
            if (!neighbour.basinVisited && neighbour.height != 9 && neighbour.height > height) {
                neighbour.basinVisited = true
                basinSize += neighbour.calculateBasinSize()
            }
        }

        return basinSize
    }
}

fun main() {

    fun loadCave(input: List<String>): Array<Point>  {
        /**
         * Parse input to obtain an array of Points.
         */
        var cave: Array<Point> = emptyArray()

        for (row in input.withIndex()) {
            for (point in row.value.withIndex()) {
                val newPoint = Point(Character.getNumericValue(point.value))

                // Add left neighbour if not first point in row
                if (point.index > 0) {
                    cave.last().addNeighbour(newPoint)
                    newPoint.addNeighbour(cave.last())
                }

                // Add top neighbour if not first row
                if (row.index > 0) {
                    val prevPointIndex = cave.lastIndex - row.value.length + 1
                    cave[prevPointIndex].addNeighbour(newPoint)
                    newPoint.addNeighbour(cave[prevPointIndex])
                }

                cave += newPoint
            }
        }

        return cave
    }

    fun part1(input: List<String>): Int {
        /**
         * Find the low points - the locations that are lower than any of its adjacent locations - and their risk level
         * (one plus their height). Return the sum of the risk levels of the low points.
         */
        val cave = loadCave(input)

        var lowPointSum = 0

        cave.forEach { point ->
            if (point.isLowPoint()) {
                lowPointSum += point.riskFactor()
            }
        }

        return lowPointSum
    }

    fun part2(input: List<String>): Int {
        /**
         * Find the three largest basins and multiply their sizes together.
         */
        val cave = loadCave(input)

        var basinSizes: Array<Int> = emptyArray()

        cave.forEach { point ->
            if (point.isLowPoint()) {
                basinSizes += point.calculateBasinSize()
            }
        }

        // Return three largest basin sizes multiplied together
        basinSizes.sortDescending()
        return basinSizes[0] * basinSizes[1] * basinSizes[2]
    }

    // Test against examples
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))

}