package util

import java.util.*

/*-----String & Char Functions-----*/

/*-----Number Parsing-----*/

fun String.allInts() = allIntsInString(this)

fun String.allLongs(): List<Long> = """\d+""".toRegex().findAll(this)
	.map { it.value.toLong() }
	.toList()

fun allIntsInString(line: String): List<Int> {
	return """-?\d+""".toRegex().findAll(line)
		.map { it.value.toInt() }
		.toList()
}

fun String.firstInt(): Int = """-?\d+""".toRegex().find(this)!!.value.toInt()

fun String.hexToBinaryString(): String {
	val num = this.uppercase(Locale.getDefault())
	var binary = ""
	for (hex in num) {
		when (hex) {
			'0' -> binary += "0000"
			'1' -> binary += "0001"
			'2' -> binary += "0010"
			'3' -> binary += "0011"
			'4' -> binary += "0100"
			'5' -> binary += "0101"
			'6' -> binary += "0110"
			'7' -> binary += "0111"
			'8' -> binary += "1000"
			'9' -> binary += "1001"
			'A' -> binary += "1010"
			'B' -> binary += "1011"
			'C' -> binary += "1100"
			'D' -> binary += "1101"
			'E' -> binary += "1110"
			'F' -> binary += "1111"
		}
	}
	return binary
}

/*-----Input Parsing-----*/

fun String.sections() = this.split("\n\n")
