package julienbirabent.apollomusic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import julienbirabent.apollomusic.data.local.dao.ExampleDao
import julienbirabent.apollomusic.data.local.dao.UserDao
import julienbirabent.apollomusic.data.local.entities.Example
import julienbirabent.apollomusic.data.local.entities.User

@Database(entities = [User::class, Example::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun exampleDao(): ExampleDao
}
