package julienbirabent.apollomusic.data.local.converters

import androidx.room.TypeConverter
import julienbirabent.apollomusic.data.local.Difficulty
import julienbirabent.apollomusic.ui.login.LoginType


class LoginTypeConverters {
    companion object {
        @JvmStatic
        @TypeConverter
        fun loginTypeToString(loginType: LoginType): String = loginType.name

        @TypeConverter
        @JvmStatic
        fun stringToLoginType(s: String): LoginType = LoginType.valueOf(s)
    }
}