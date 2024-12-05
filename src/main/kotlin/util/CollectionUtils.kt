package util

fun <T> List<T>.pairwise() = mapIndexed { index, first -> drop(index + 1).map { second -> first to second } }.flatten()
fun <T, R> List<T>.pairwise(transform: (T, T) -> R) = mapIndexed { index, first -> drop(index + 1).map { second -> transform(first, second) } }.flatten()

fun <T> List<T>.middle(): T {
	if (size % 2 == 0) error("List must have an odd number of elements")
	return this[size / 2]
}

fun <E> permutations(list: List<E>, length: Int? = null): Sequence<List<E>> = sequence {
	val n = list.size
	val r = length ?: list.size

	val indices = list.indices.toMutableList()
	val cycles = (n downTo (n - r)).toMutableList()
	yield(indices.take(r).map { list[it] })

	while (true) {
		var broke = false
		for (i in (r - 1) downTo 0) {
			cycles[i]--
			if (cycles[i] == 0) {
				val end = indices[i]
				for (j in i until indices.size - 1) {
					indices[j] = indices[j + 1]
				}
				indices[indices.size - 1] = end
				cycles[i] = n - i
			} else {
				val j = cycles[i]
				val tmp = indices[i]
				indices[i] = indices[-j + indices.size]
				indices[-j + indices.size] = tmp
				yield(indices.take(r).map { list[it] })
				broke = true
				break
			}
		}
		if (!broke) {
			break
		}
	}
}
