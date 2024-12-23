package setup

object ConsoleUtils {

	val header = "\n" +
			"   ___       _                             _                ___       __             ___               _           \n" +
			"  /   \\   __| |   __ __    ___    _ _     | |_      o O O  / _ \\     / _|    o O O  / __|    ___    __| |    ___   \n" +
			"  | - |  / _` |   \\ V /   / -_)  | ' \\    |  _|    o      | (_) |   |  _|   o      | (__    / _ \\  / _` |   / -_)  \n" +
			"  |_|_|  \\__,_|   _\\_/_   \\___|  |_||_|   _\\__|   TS__[O]  \\___/   _|_|_   TS__[O]  \\___|   \\___/  \\__,_|   \\___|  \n" +
			"_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"| {======|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \n" +
			"\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'./o--000'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' \n" +
			"\n"
	const val BLACK = "\u001b[0;30m" // BLACK
	const val RED = "\u001b[0;31m" // RED
	const val GREEN = "\u001b[0;32m" // GREEN
	const val YELLOW = "\u001b[0;33m" // YELLOW
	const val BLUE = "\u001b[0;34m" // BLUE
	const val PURPLE = "\u001b[0;35m" // PURPLE
	const val CYAN = "\u001b[0;36m" // CYAN
	const val WHITE = "\u001b[0;37m" // WHITE

	// Bold
	const val BLACK_BOLD = "\u001b[1;30m" // BLACK

	const val RED_BOLD = "\u001b[1;31m" // RED

	const val GREEN_BOLD = "\u001b[1;32m" // GREEN

	const val YELLOW_BOLD = "\u001b[1;33m" // YELLOW

	const val BLUE_BOLD = "\u001b[1;34m" // BLUE

	const val BLACK_BACKGROUND = "\u001b[40m" // BLACK

	const val RED_BACKGROUND = "\u001b[41m" // RED

	const val GREEN_BACKGROUND = "\u001b[42m" // GREEN

	const val YELLOW_BACKGROUND = "\u001b[43m" // YELLOW

	const val BLUE_BACKGROUND = "\u001b[44m" // BLUE

	const val PURPLE_BACKGROUND = "\u001b[45m" // PURPLE

	const val CYAN_BACKGROUND = "\u001b[46m" // CYAN

	const val WHITE_BACKGROUND = "\u001b[47m" // WHITE

	// High Intensity
	const val BLACK_BRIGHT = "\u001b[0;90m" // BLACK

	const val RED_BRIGHT = "\u001b[0;91m" // RED

	const val GREEN_BRIGHT = "\u001b[0;92m" // GREEN

	const val YELLOW_BRIGHT = "\u001b[0;93m" // YELLOW

	const val BLUE_BRIGHT = "\u001b[0;94m" // BLUE

	const val PURPLE_BRIGHT = "\u001b[0;95m" // PURPLE

	const val CYAN_BRIGHT = "\u001b[0;96m" // CYAN

	const val WHITE_BRIGHT = "\u001b[0;97m" // WHITE

	// Bold High Intensity
	const val BLACK_BOLD_BRIGHT = "\u001b[1;90m" // BLACK

	const val RED_BOLD_BRIGHT = "\u001b[1;91m" // RED

	const val GREEN_BOLD_BRIGHT = "\u001b[1;92m" // GREEN

	const val YELLOW_BOLD_BRIGHT = "\u001b[1;93m" // YELLOW

	const val BLUE_BOLD_BRIGHT = "\u001b[1;94m" // BLUE

	const val PURPLE_BOLD_BRIGHT = "\u001b[1;95m" // PURPLE

	const val CYAN_BOLD_BRIGHT = "\u001b[1;96m" // CYAN

	const val WHITE_BOLD_BRIGHT = "\u001b[1;97m" // WHITE

	// High Intensity backgrounds
	const val BLACK_BACKGROUND_BRIGHT = "\u001b[0;100m" // BLACK

	const val RED_BACKGROUND_BRIGHT = "\u001b[0;101m" // RED

	const val GREEN_BACKGROUND_BRIGHT = "\u001b[0;102m" // GREEN

	const val YELLOW_BACKGROUND_BRIGHT = "\u001b[0;103m" // YELLOW

	const val BLUE_BACKGROUND_BRIGHT = "\u001b[0;104m" // BLUE

	const val PURPLE_BACKGROUND_BRIGHT = "\u001b[0;105m" // PURPLE

	const val CYAN_BACKGROUND_BRIGHT = "\u001b[0;106m" // CYAN

	const val WHITE_BACKGROUND_BRIGHT =
		"\u001b[0;107m" // WHITElic static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE

	const val CYAN_BOLD = "\u001b[1;36m" // CYAN

	const val WHITE_BOLD = "\u001b[1;37m" // WHITE

	// Underline
	const val BLACK_UNDERLINED = "\u001b[4;30m" // BLACK

	const val RED_UNDERLINED = "\u001b[4;31m" // RED

	const val GREEN_UNDERLINED = "\u001b[4;32m" // GREEN

	const val YELLOW_UNDERLINED = "\u001b[4;33m" // YELLOW

	const val BLUE_UNDERLINED = "\u001b[4;34m" // BLUE

	const val PURPLE_UNDERLINED = "\u001b[4;35m" // PURPLE

	const val CYAN_UNDERLINED = "\u001b[4;36m" // CYAN

	const val WHITE_UNDERLINED = "\u001b[4;37m" // WHITE

	const val RESET = "\u001b[0m" // Text Reset

	val blinkColors = listOf(RED, GREEN, YELLOW, BLUE, PURPLE, CYAN)

}
