package challenges

import java.io.File


fun main() {
    val fileLInes = File(ClassLoader.getSystemResource("inputDay3.txt").path).useLines { it.toList() }
    println("The sum of all numbers adjacent to a symbol is: ${findSumOfNumbersAdjacentToASymbol(fileLInes)}")
}

private fun findSumOfNumbersAdjacentToASymbol(fileLines: List<String>): Int {
    val engineSchematic = fileLines.map { it.toCharArray() }.toTypedArray()
    val validParts = ArrayList<Int>()
    engineSchematic.forEachIndexed { lineNumber, parts ->
        var initialPosition: Int
        var finalPosition = 0

        for (index in parts.indices) {
            var partNumber = ""
            if (index != 0 && index <= finalPosition) continue
            initialPosition = index
            if (parts[index].isDigit()) {
                var i = index
                while (i < parts.size && parts[i].isDigit()) {
                    partNumber += parts[i]
                    i++
                }
                finalPosition = i

                if (hasAdjacent(
                        initialPosition,
                        finalPosition-1,
                        engineSchematic,
                        lineNumber
                    )
                ) validParts.add(partNumber.toInt())
            }
        }
    }
    return validParts.sum()
}

private fun hasAdjacent(initial: Int, final: Int, engineSchematic: Array<CharArray>, line: Int): Boolean {
    return isWithinMatrixBounds(engineSchematic, line-1, initial) && isSymbol(engineSchematic[line-1][initial])
            || isWithinMatrixBounds(engineSchematic, line-1, initial-1) && isSymbol(engineSchematic[line-1][initial-1])
            || isWithinMatrixBounds(engineSchematic, line, initial-1) && isSymbol(engineSchematic[line][initial-1])
            || isWithinMatrixBounds(engineSchematic, line+1, initial-1) && isSymbol(engineSchematic[line+1][initial-1])
            || isWithinMatrixBounds(engineSchematic, line+1, initial) && isSymbol(engineSchematic[line+1][initial])

            || isWithinMatrixBounds(engineSchematic, line+1, final) && isSymbol(engineSchematic[line+1][final])
            || isWithinMatrixBounds(engineSchematic, line+1, final+1) && isSymbol(engineSchematic[line+1][final+1])
            || isWithinMatrixBounds(engineSchematic, line, final+1) && isSymbol(engineSchematic[line][final+1])
            || isWithinMatrixBounds(engineSchematic, line-1, final+1) && isSymbol(engineSchematic[line-1][final+1])
            || isWithinMatrixBounds(engineSchematic, line-1, final) && isSymbol(engineSchematic[line-1][final])

            || isWithinMatrixBounds(engineSchematic, line+1, final-1) && isSymbol(engineSchematic[line+1][final-1])
            || isWithinMatrixBounds(engineSchematic, line-1, final-1) && isSymbol(engineSchematic[line-1][final-1])

}

fun isWithinMatrixBounds(matrix: Array<CharArray>, row: Int, col: Int): Boolean {
    return row >= 0 && row < matrix.size && col >= 0 && col < matrix[0].size
}

fun isSymbol(value: Char): Boolean {
    return value != '.' &&  !value.isDigit()
}


/**
 * Challenge part 3
 *
 * The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.
 *
 * The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 *
 * Here is an example engine schematic:
 *
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 * In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
 *
 * Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?
 *
 * -----------
 *
 *part 2
 *
 * The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
 *
 * This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out which gear needs to be replaced.
 *
 * Consider the same engine schematic again:
 *
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 * In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
 *
 * What is the sum of all of the gear ratios in your engine schematic?
 */