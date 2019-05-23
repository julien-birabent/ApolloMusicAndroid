package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.data.api.services.ExampleService
import julienbirabent.apollomusic.data.local.dao.ExampleDao
import julienbirabent.apollomusic.data.repository.ExampleRepository
import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.SchedulerProvider
import javax.inject.Singleton


@Module(includes = [DatabaseModule::class, ApiModule::class])
class DataModule{


    //Inject Repositories here
    @Singleton
    @Provides
    fun provideExampleRepository(exampleDao: ExampleDao,
                                 exampleService: ExampleService,
                                 appExecutors: AppExecutors,
                                 schedulerProvider: SchedulerProvider
    ) : ExampleRepository{
        return ExampleRepository(exampleDao, exampleService,appExecutors,schedulerProvider)
    }
}