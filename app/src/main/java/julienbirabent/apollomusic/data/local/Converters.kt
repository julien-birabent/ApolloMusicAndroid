package julienbirabent.apollomusic.data.local

import androidx.room.TypeConverter
import julienbirabent.apollomusic.ui.login.LoginType

class RoomConverters {
    companion object{
        @JvmStatic
        @TypeConverter
        fun loginTypeToString(loginType: LoginType): String = loginType.name

        @TypeConverter
        @JvmStatic
        fun stringToLoginType(s: String): LoginType = LoginType.valueOf(s)
    }
}