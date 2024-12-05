package util

/*------ pairwise ------*/

fun <T> List<T>.pairwise() = mapIndexed { index, first -> drop(index + 1).map { second -> first to second } }.flatten()
fun <T, R> List<T>.pairwise(transform: (T, T) -> R) = mapIndexed { index, first -> drop(index + 1).map { second -> transform(first, second) } }.flatten()

/*------ middle ------*/

fun <T> List<T>.middle(): T {
	if (size % 2 == 0) error("List must have an odd number of elements")
	return this[size / 2]
}
