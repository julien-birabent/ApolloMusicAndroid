package julienbirabent.apollomusic.app

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import julienbirabent.apollomusic.BuildConfig
import julienbirabent.apollomusic.di.component.DaggerApplicationComponent
import javax.inject.Inject


class ApolloMusicApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    init {
        instance = this
    }

    companion object {
        private var instance: ApolloMusicApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .application(this)
            .appConstants(getAppConstants())
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingInjector

    private fun getAppConstants(): AppConstants {
        return if (BuildConfig.DEBUG) DebugAppConstants() else ReleaseAppConstants()
    }

}
