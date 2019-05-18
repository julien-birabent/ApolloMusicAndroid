package julienbirabent.apollomusic.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton
import dagger.Provides
import android.content.SharedPreferences
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.thread.SchedulerProvider


@Module
class AppModule{

    private val SHARED_PACKAGE = "base_shared_preferences"

    @Singleton
    @Provides
    @Named("ApplicationContext")
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}
