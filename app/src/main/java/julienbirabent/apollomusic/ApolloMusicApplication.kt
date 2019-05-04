package julienbirabent.apollomusic

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import julienbirabent.apollomusic.di.component.ApplicationComponent
import julienbirabent.apollomusic.di.component.DaggerApplicationComponent
import javax.inject.Inject


class ApolloMusicApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingInjector
    }
}
