package util

data class Point3(val x: Int, val y: Int, val z: Int) {

	operator fun plus(other: Point3) = Point3(other.x + x, other.y + y, other.z + z)
	operator fun minus(other: Point3) = Point3(x - other.x, y - other.y, z - other.z)
	operator fun times(n: Int) = Point3(x * n, y * n, z * n)

	fun neighbors(): List<Point3> = listOf(
		Point3(x + 1, y, z), Point3(x - 1, y, z),
		Point3(x, y + 1, z), Point3(x, y - 1, z),
		Point3(x, y, z + 1), Point3(x, y, z - 1),
	)
}
