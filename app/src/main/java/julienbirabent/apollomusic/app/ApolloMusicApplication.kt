package julienbirabent.apollomusic.app

import android.app.Activity
import android.app.Application
import android.os.Debug
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import julienbirabent.apollomusic.BuildConfig
import julienbirabent.apollomusic.di.component.DaggerApplicationComponent
import javax.inject.Inject


class ApolloMusicApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .application(this)
            .appConstants(getAppConstants())
            .build()
            .inject(this)


    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingInjector

    private fun getAppConstants():AppConstants{
        return if (BuildConfig.DEBUG) DebugAppConstants() else ReleaseAppConstants()
    }
}
