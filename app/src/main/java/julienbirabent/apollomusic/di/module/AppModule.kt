package julienbirabent.apollomusic.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class AppModule{

    @Binds
    @Named("ApplicationContext")
    abstract fun provideContext(application: Application): Context
}
