package julienbirabent.apollomusic.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton
import dagger.Provides
import android.content.SharedPreferences
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.app.ReleaseAppConstants
import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.thread.SchedulerProvider


@Module
class AppModule{

    @Singleton
    @Provides
    @Named("ApplicationContext")
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
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Singleton
    @Provides
    fun provideAppExecutor(): AppExecutors = AppExecutors()
}
