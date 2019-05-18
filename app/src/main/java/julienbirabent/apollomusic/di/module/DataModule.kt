package julienbirabent.apollomusic.di.module

import dagger.Module


@Module(includes = [DatabaseModule::class, ApiModule::class])
class DataModule{


    //Inject Repositories here

}