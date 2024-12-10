package setup

class DayFactory {
	companion object {

		fun getDayObject(day: Int, input: String): Day<out Any> {
			return Days.days[day]?.invoke(input) ?: error("this day doesnt have a solution yet")
		}
	}

}
