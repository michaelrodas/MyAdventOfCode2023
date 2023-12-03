package challenges

import java.io.File


fun main() {
    val fileLInes = File(ClassLoader.getSystemResource("inputDay1.txt").path).useLines { it.toList() }
    println("The sum of all number combinations is: ${findSumOfNumbers(fileLInes)}")
}

fun findSumOfNumbers(fileLines: List<String>) =
    fileLines.sumOf { line ->
        var temp = line //These 2 lines are the solution to second challenge of day 1
        NUMBERS.entries.map { temp = temp.replace(it.name, it.digit, true) }

        (getFirstNumber(temp).toString() + getLastNumber(temp).toString()).toInt()
    }

private fun getFirstNumber(line: String) = line.first { it.isDigit() }

private fun getLastNumber(line: String) = line.last { it.isDigit() }

enum class NUMBERS(val digit: String) {
    ONE("o1e"),
    TWO("t2o"),
    THREE("t3e"),
    FOUR("f4r"),
    FIVE("f5e"),
    SIX("s6x"),
    SEVEN("s7n"),
    EIGHT("e8t"),
    NINE("n9e")
}

/**
 * Challenge 1
 *
 * The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover.
 * On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
 *
 * For example:
 *
 * 1abc2
 * pqr3stu8vwx
 * a1b2c3d4e5f
 * treb7uchet
 *
 * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
 *
 * Consider your entire calibration document. What is the sum of all of the calibration values?
 *
 * -----------------------
 *
 * Challenge 2
 *
 * Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".
 *
 * Equipped with this new information, you now need to find the real first and last digit on each line. For example:
 *
 * two1nine
 * eightwothree
 * abcone2threexyz
 * xtwone3four
 * 4nineeightseven2
 * zoneight234
 * 7pqrstsixteen
 * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.
 *
 * What is the sum of all of the calibration values?
 *
 */
