package julienbirabent.apollomusic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import julienbirabent.apollomusic.data.local.converters.DateConverters
import julienbirabent.apollomusic.data.local.converters.EnumConverters
import julienbirabent.apollomusic.data.local.dao.ExampleDao
import julienbirabent.apollomusic.data.local.dao.UserDao
import julienbirabent.apollomusic.data.local.entities.ExampleEntity
import julienbirabent.apollomusic.data.local.entities.UserEntity
import java.text.SimpleDateFormat

@Database(entities = [UserEntity::class, ExampleEntity::class], version = 1)
@TypeConverters(EnumConverters::class, DateConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        lateinit var dateFormat: SimpleDateFormat
    }
    abstract fun userDao(): UserDao
    abstract fun exampleDao(): ExampleDao
}
