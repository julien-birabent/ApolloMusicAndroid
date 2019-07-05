package julienbirabent.apollomusic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import julienbirabent.apollomusic.data.local.converters.DateConverters
import julienbirabent.apollomusic.data.local.converters.DifficultyConverter
import julienbirabent.apollomusic.data.local.converters.LoginTypeConverters
import julienbirabent.apollomusic.data.local.converters.ListConverter
import julienbirabent.apollomusic.data.local.dao.*
import julienbirabent.apollomusic.data.local.entities.*
import java.text.SimpleDateFormat

@Database(
    entities = [UserEntity::class, ExampleEntity::class, PracticeEntity::class,
        ObjectiveEntity::class, ExerciseEntity::class, CriteriaEntity::class,
        ObjectiveExerciseJoin::class, ObjectiveCriteriaJoin::class],
    version = 1
)
@TypeConverters(LoginTypeConverters::class, DateConverters::class, ListConverter::class, DifficultyConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        lateinit var dateFormat: SimpleDateFormat
    }

    abstract fun userDao(): UserDao
    abstract fun exampleDao(): ExampleDao
    abstract fun practiceDao(): PracticeDao
    abstract fun objectiveDao(): ObjectiveDao
    abstract fun criteriaDao(): CriteriaDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun objectiveCriteriaJoinDao(): ObjectiveCriteriaJoinDao
    abstract fun objectiveExerciseJoinDao(): ObjectiveExerciseJoinDao
}
