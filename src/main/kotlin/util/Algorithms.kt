package util

/**
 * Uses binary search to find the first index in the range [start, end) that satisfies the given predicate.
 */
fun binarySearchLowerBound(start: Int, end: Int, predicate: ((Int) -> Boolean)): Int {
	if (start == end) return start
	if (end < start) throw IllegalArgumentException("End must be greater than or equal to start")

	var low = start
	var high = end
	while (low < high) {
		val mid = low + (high - low) / 2
		if (predicate(mid)) high = mid
		else low = mid + 1
	}
	return low
}
