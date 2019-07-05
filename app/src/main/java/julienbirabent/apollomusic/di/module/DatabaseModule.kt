package julienbirabent.apollomusic.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.local.AppDatabase
import julienbirabent.apollomusic.data.local.dao.*
import julienbirabent.apollomusic.di.annotation.DatabaseInfo
import java.text.SimpleDateFormat
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context, dateFormat: SimpleDateFormat): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
            .apply {
                AppDatabase.dateFormat = dateFormat
            }
    }

    @Provides
    @DatabaseInfo
    fun provideDbName(appConstants: AppConstants): String {
        return appConstants.dbName()
    }

    @Provides
    @DatabaseInfo
    fun provideDbVersion(appConstants: AppConstants): Int {
        return appConstants.dbVersion()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideObjectiveCriteriaJoinDao(appDatabase: AppDatabase): ObjectiveCriteriaJoinDao {
        return appDatabase.objectiveCriteriaJoinDao()
    }

    @Singleton
    @Provides
    fun provideObjectiveExerciseJoinDao(appDatabase: AppDatabase): ObjectiveExerciseJoinDao {
        return appDatabase.objectiveExerciseJoinDao()
    }

    @Singleton
    @Provides
    fun practiceDao(appDatabase: AppDatabase): PracticeDao = appDatabase.practiceDao()

    @Singleton
    @Provides
    fun objectiveDao(appDatabase: AppDatabase): ObjectiveDao = appDatabase.objectiveDao()

    @Singleton
    @Provides
    fun criteriaDao(appDatabase: AppDatabase): CriteriaDao = appDatabase.criteriaDao()

    @Singleton
    @Provides
    fun provideExampleDao(appDatabase: AppDatabase): ExampleDao {
        return appDatabase.exampleDao()
    }

    @Singleton
    @Provides
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao {
        return appDatabase.exerciseDao()
    }
}