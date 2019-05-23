package julienbirabent.apollomusic.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.thread.SchedulerProvider
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context, appConstants: AppConstants): SharedPreferences {
        return context.getSharedPreferences(appConstants.sharedPrefName(), Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton
    @Provides
    fun provideAppExecutor(): AppExecutors = AppExecutors()
}
