package julienbirabent.apollomusic.data.local

enum class Difficulty(var difficulty: Int, var difficultyString: String) {
    EASY(1, "Easy"),
    INTERMEDIATE(2, "Intermediate"),
    HARD(3, "Hard"),
    GODLIKE(4, "Godlike")
}