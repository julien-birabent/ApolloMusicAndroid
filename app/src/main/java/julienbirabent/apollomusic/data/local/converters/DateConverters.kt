package julienbirabent.apollomusic.data.local.converters

import androidx.room.TypeConverter
import julienbirabent.apollomusic.data.local.AppDatabase
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateConverters {

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let {
            return try {
                AppDatabase.dateFormat.format(it)
            } catch (e: ParseException) {
                null
            }
        }
    }

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let {
            return try {
                AppDatabase.dateFormat.parse(it)
            } catch (e: ParseException) {
                null
            }
        }
    }
}