package util

infix fun IntRange.overlaps(other: IntRange): Boolean =
	first in other || last in other || other.first in this || other.last in this

infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this
infix fun IntRange.adjoint(other: IntRange): Boolean = this.last + 1 == other.first || other.last + 1 == this.first
