package julienbirabent.apollomusic.data

import androidx.room.Database
import androidx.room.RoomDatabase
import julienbirabent.apollomusic.data.local.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
