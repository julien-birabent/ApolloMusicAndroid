package julienbirabent.apollomusic.data.local

enum class Difficulty(var difficulty: Int, var difficultyString: String) {
    UNDEFINED(0, "Undefined"),
    EASY(1, "Easy"),
    INTERMEDIATE(2, "Intermediate"),
    HARD(3, "Hard"),
    GODLIKE(4, "Godlike");

    companion object {
        @JvmStatic
        fun intToString(value: Int): String {
            return when (value) {
                1 -> Difficulty.EASY.difficultyString
                2 -> Difficulty.INTERMEDIATE.difficultyString
                3 -> Difficulty.HARD.difficultyString
                4 -> Difficulty.GODLIKE.difficultyString
                else -> Difficulty.UNDEFINED.difficultyString
            }
        }
    }

}