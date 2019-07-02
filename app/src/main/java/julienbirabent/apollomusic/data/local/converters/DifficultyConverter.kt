package julienbirabent.apollomusic.data.local.converters

import androidx.room.TypeConverter
import julienbirabent.apollomusic.data.local.Difficulty


class DifficultyConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun intToDifficulty(value: String): Difficulty {
            return when (value) {
                "1" -> Difficulty.EASY
                "2" -> Difficulty.INTERMEDIATE
                "3" -> Difficulty.HARD
                "4" -> Difficulty.GODLIKE
                else -> Difficulty.UNDEFINED
            }
        }

        @JvmStatic
        @TypeConverter
        fun difficultyToInt(difficulty: Difficulty): String = difficulty.difficulty.toString()
    }
}
