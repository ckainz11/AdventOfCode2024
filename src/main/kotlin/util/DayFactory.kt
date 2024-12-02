package util

import days.Day
import days.day01.Day1
import days.day02.Day2


class DayFactory {
    companion object {
        val days = mapOf(
            1 to ::Day1,
            2 to ::Day2
        )

        fun getDayObject(day: Int, input: String): Day<out Any> {
            return days[day]?.invoke(input) ?: error("this day doesnt have a solution yet")
        }
    }


}
