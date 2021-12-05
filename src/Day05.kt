import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs
import kotlin.math.sign

/**
 * Day 5: Hydrothermal Venture
 */


fun main() {

    fun parseInput(input: List<String>): Pair<Array<Pair<List<Int>, List<Int>>>, Array<IntArray>>  {
        var lineSegments: Array<Pair<List<Int>, List<Int>>> = emptyArray()
        var maxX = 0
        var maxY = 0

        for (i in input) {
            val (start, end) = i.split(" -> ")
            val startCoords = start.split(",").map{it.toInt()}
            val endCoords = end.split(",").map{it.toInt()}
            maxX = max(maxX, max(startCoords[0], endCoords[0]))
            maxY = max(maxY, max(startCoords[1], endCoords[1]))
            lineSegments += Pair(startCoords, endCoords)
        }

        // Construct empty grid of required size
        val grid = Array(maxX + 1) { IntArray(maxY + 1)}

        return Pair(lineSegments, grid)
    }

    fun calculateOverlaps(input: List<String>, diagonal: Boolean = false): Int {
        /**
         * Determine the number of points where at least two lines overlap.
         */
        val (lineSegments, grid) = parseInput(input)

        var twoOrMoreOverlaps = 0

        for (segment in lineSegments) {
            val start = segment.first
            val end = segment.second

            // Check if x coordinates match
            if (start[0] == end[0]) {
                // Mark points in grid
                val minY = min(start[1], end[1])
                val maxY = max(start[1], end[1])
                for (y in minY .. maxY) {
                    grid[start[0]][y] += 1

                    if (grid[start[0]][y] == 2) {
                        // Two lines covering this point
                        twoOrMoreOverlaps += 1
                    }
                }
            } else if (start[1] == end[1]) {
                // y coordinates match, mark in grid
                val minX = min(start[0], end[0])
                val maxX = max(start[0], end[0])
                for (x in minX .. maxX) {
                    grid[x][start[1]] += 1

                    if (grid[x][start[1]] == 2) {
                        // Two lines covering this point
                        twoOrMoreOverlaps += 1
                    }
                }
            } else if (diagonal) {
                val xDiff = end[0] - start[0]
                val yDiff = end[1] - start[1]
                
                // Line is diagonal if difference between x and y are the same
                if (abs(xDiff) != abs(yDiff)) {
                    continue
                }

                val yCoefficient: Int
                var y: Int

                if (xDiff.sign == yDiff.sign) {
                    // x and y values are both either increasing or decreasing
                    yCoefficient = 1
                    y = min(start[1], end[1])
                } else {
                    // One of either x or y is increasing, the other is decreasing
                    yCoefficient = -1
                    y = max(start[1], end[1])
                }

                val minX = min(start[0], end[0])
                val maxX = max(start[0], end[0])
                for (x in minX .. maxX) {
                    grid[x][y] += 1

                    if (grid[x][y] == 2) {
                        // Two lines covering this point
                        twoOrMoreOverlaps += 1
                    }
                    y += 1 * yCoefficient
                }
            }
        }

        return twoOrMoreOverlaps
    }

    fun part1(input: List<String>): Int {
        /**
         * Determine the number of points where at least two horizontal or vertical lines overlap.
         */
        return calculateOverlaps(input)
    }

    fun part2(input: List<String>): Int {
        /**
         * Determine the number of points where at least two horizontal, vertical or diagonal lines overlap.
         */
        return calculateOverlaps(input, true)
    }

    // Test against examples
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
