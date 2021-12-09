import java.util.Collections.min

/**
 * Day 9: Smoke Basin
 */

class Point(private val height: Int) {
    /**
     * Class representing a point in the cave.
     */
    private var neighbours: MutableSet<Point> = mutableSetOf()

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
}

fun main() {

    fun loadGrid(input: List<String>): Array<Point>  {
        /**
         * Parse input to obtain an array of Points.
         */

        var grid: Array<Point> = emptyArray()

        for (row in input.withIndex()) {
            for (point in row.value.withIndex()) {
                val newPoint = Point(Character.getNumericValue(point.value))

                // Add left neighbour if not first point in row
                if (point.index > 0) {
                    grid.last().addNeighbour(newPoint)
                    newPoint.addNeighbour(grid.last())
                }

                // Add top neighbour if not first row
                if (row.index > 0) {
                    val prevPointIndex = grid.lastIndex - row.value.length + 1
                    grid[prevPointIndex].addNeighbour(newPoint)
                    newPoint.addNeighbour(grid[prevPointIndex])
                }

                grid += newPoint
            }
        }

        return grid
    }

    fun part1(input: List<String>): Int {
        /**
         * Find the low points - the locations that are lower than any of its adjacent locations - and their risk level
         * (one plus their height). Return the sum of the risk levels of the low points.
         */
        val grid = loadGrid(input)

        var lowPointSum = 0

        grid.forEach { point ->
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
        val grid = loadGrid(input)
        // TODO
        return 0
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))

}