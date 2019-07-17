package julienbirabent.apollomusic.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.api.services.CriteriaAPI
import julienbirabent.apollomusic.data.api.services.UserAPI
import julienbirabent.apollomusic.data.local.dao.CriteriaDao
import julienbirabent.apollomusic.data.local.dao.UserDao
import julienbirabent.apollomusic.data.repository.CriteriaRepository
import julienbirabent.apollomusic.data.repository.UserRepository
import javax.inject.Singleton


@Module(includes = [DatabaseModule::class, ApiModule::class, AppModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideUserRepo(
        userAPI: UserAPI,
        userDao: UserDao,
        sp: SharedPreferences,
        appConstants: AppConstants
    ): UserRepository {
        return UserRepository(
            userAPI, userDao, sp, appConstants
        )
    }
}