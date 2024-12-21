package util

/*------ pairwise ------*/

fun <T> List<T>.pairwise() = mapIndexed { index, first -> drop(index + 1).map { second -> first to second } }.flatten()
fun <T, R> List<T>.pairwise(transform: (T, T) -> R) = mapIndexed { index, first -> drop(index + 1).map { second -> transform(first, second) } }.flatten()

/*------ middle ------*/

fun <T> List<T>.middle(): T {
	if (size % 2 == 0) error("List must have an odd number of elements")
	return this[size / 2]
}

/*----- permutations -----*/

fun <T> List<T>.permutations(): List<List<T>> {
	return if (this.size == 1) listOf(this)
	else this.flatMap { i -> (this - i).permutations().map { listOf(i) + it } }
}

/*----- String Joining -----*/

fun <T> Array<T>.join() = this.joinToString("")
fun <T> Array<T>.join(separator: String) = this.joinToString(separator)
fun CharArray.join() = this.joinToString("")
fun CharArray.join(separator: String) = this.joinToString(separator)
fun <T> Iterable<T>.join() = this.joinToString("")
fun <T> Iterable<T>.join(separator: String) = this.joinToString(separator)
