package julienbirabent.apollomusic.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.api.services.CriteriaAPI
import julienbirabent.apollomusic.data.local.dao.CriteriaDao
import julienbirabent.apollomusic.data.repository.CriteriaRepository
import julienbirabent.apollomusic.data.repository.UserRepository
import javax.inject.Singleton


@Module(includes = [DatabaseModule::class, ApiModule::class, AppModule::class])
class DataModule {

   /* @Singleton
    @Provides
    fun provideCriteriaRepository(
        userRepository: UserRepository,
        criteriaDao: CriteriaDao,
        criteriaAPI: CriteriaAPI, appConstants: AppConstants
    ): CriteriaRepository {
        return CriteriaRepository(
            userRepository, criteriaDao, criteriaAPI, appConstants
        )
    }*/
}