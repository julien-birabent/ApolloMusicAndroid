package julienbirabent.apollomusic.app

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import julienbirabent.apollomusic.BuildConfig
import julienbirabent.apollomusic.di.component.ApplicationComponent
import julienbirabent.apollomusic.di.component.DaggerApplicationComponent
import javax.inject.Inject


class ApolloMusicApplication : Application(), HasActivityInjector {

    private lateinit var component: ApplicationComponent

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    init {
        instance = this
    }

    companion object {
        private var instance: ApolloMusicApplication? = null

        fun applicationComponent(): ApplicationComponent? = instance?.component
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .application(this)
            .appConstants(getAppConstants())
            .build()
        component.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingInjector

    private fun getAppConstants(): AppConstants {
        return if (BuildConfig.DEBUG) DebugAppConstants() else ReleaseAppConstants()
    }
}
