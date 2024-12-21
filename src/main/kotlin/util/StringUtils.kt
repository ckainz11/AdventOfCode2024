package util

/*-----Number Parsing-----*/

fun String.allInts() = allNumbersInString(this).map { it.toInt() }

fun String.allLongs(): List<Long> = allNumbersInString(this).map { it.toLong() }

fun String.firstInt(): Int = allInts().first()

private fun allNumbersInString(line: String): List<String> {
	return """-?\d+""".toRegex().findAll(line).toList().map { it.value }
}

/*-----Input Parsing-----*/

fun String.sections() = this.split("\n\n")

fun String.asMatrix() = this.lines().map { it.toCharArray().toList() }

/*----- permutations -----*/

fun String.permutations() = this.toList().permutations().map { it.join() }
