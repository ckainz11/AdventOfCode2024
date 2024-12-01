package util

import days.Day
import days.day01.Day1


class DayFactory {
    companion object {
        val days = mapOf(
            1 to ::Day1,
        )

        fun getDayObject(day: Int, input: String): Day<out Any> {
            return days[day]?.invoke(input) ?: error("this day doesnt have a solution yet")
        }
    }


}
