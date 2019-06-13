package julienbirabent.apollomusic.data.local.converters

import androidx.room.TypeConverter
import julienbirabent.apollomusic.data.local.Difficulty
import julienbirabent.apollomusic.ui.login.LoginType


class EnumConverters {
    companion object {
        @JvmStatic
        @TypeConverter
        fun loginTypeToString(loginType: LoginType): String = loginType.name

        @TypeConverter
        @JvmStatic
        fun stringToLoginType(s: String): LoginType = LoginType.valueOf(s)

        @JvmStatic
        @TypeConverter
        fun difficultyToInt(difficulty: Difficulty): Int = difficulty.difficulty

        @TypeConverter
        @JvmStatic
        fun intToDifficulty(value: Int): Difficulty? = Difficulty.values().find { it.difficulty == value }
    }
}