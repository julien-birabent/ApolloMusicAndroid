package julienbirabent.apollomusic.di.module

import dagger.Module


@Module(includes = [DatabaseModule::class, ApiModule::class])
class DataModule{


    //Inject Repositories here
    /*@Singleton
    @Provides
    fun provideExampleRepository(exampleDao: ExampleDao,
                                 exampleService: ExampleAPI,
                                 appExecutors: AppExecutors,
                                 schedulerProvider: SchedulerProvider
    ) : ExampleRepository{
        return ExampleRepository(exampleDao, exampleService,appExecutors,schedulerProvider)
    }*/
}