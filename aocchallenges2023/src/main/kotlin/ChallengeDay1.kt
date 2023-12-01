import java.io.File

class ChallengeDay1 {

    fun findSumOfNumbers(fileLines: List<String>) =
        fileLines.sumOf { line ->
            var temp = line
            NUMBERS.entries.map { temp = temp.replace(it.name, it.digit, true) }

            (getFirstNumber(temp).toString() + getLastNumber(temp).toString()).toInt()
        }

    private fun getFirstNumber(line: String) = line.first { it.isDigit() }

    private fun getLastNumber(line: String) = line.last { it.isDigit() }
}

fun main(args: Array<String>) {

    val fileLInes = File(ClassLoader.getSystemResource("inputDay1.txt").path).useLines { it.toList() }
    println("The sum of all number combinations is: ${ChallengeDay1().findSumOfNumbers(fileLInes)}")
}

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
