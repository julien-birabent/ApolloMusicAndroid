package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.data.repository.CriteriaRepository
import julienbirabent.apollomusic.data.repository.UserRepository
import javax.inject.Singleton


@Module(includes = [DatabaseModule::class, ApiModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideCriteriaRepository(
        userRepository: UserRepository
    ): CriteriaRepository {
        return CriteriaRepository(userRepository)
    }
}