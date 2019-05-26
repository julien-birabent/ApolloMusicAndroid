package julienbirabent.apollomusic.data.local.converters

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateConverters {

    @Inject lateinit var dateFormat: SimpleDateFormat

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let {
            try {
                dateFormat.format(it)
            } catch (e: ParseException) {
                return null
            }
        }
    }

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let {
            try {
                dateFormat.parse(it)
            } catch (e: ParseException) {
                return null
            }
        }
    }
}