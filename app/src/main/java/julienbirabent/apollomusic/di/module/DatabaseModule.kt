package julienbirabent.apollomusic.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.AppDatabase
import julienbirabent.apollomusic.data.local.UserDao
import julienbirabent.apollomusic.di.annotation.DatabaseInfo
import javax.inject.Singleton

@Module
class DatabaseModule{

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @DatabaseInfo
    fun provideDbName(appConstants: AppConstants): String{
        return appConstants.dbName()
    }

    @Provides
    @DatabaseInfo
    fun provideDbVersion(appConstants: AppConstants): Int{
        return appConstants.dbVersion()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao{
        return appDatabase.userDao()
    }
}