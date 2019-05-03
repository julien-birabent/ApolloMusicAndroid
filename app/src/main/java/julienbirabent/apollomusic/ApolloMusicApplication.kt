package julienbirabent.apollomusic

import dagger.android.support.DaggerApplication
import julienbirabent.apollomusic.di.component.DaggerApplicationComponent

class ApolloMusicApplication : DaggerApplication() {

    private val applicationInjector = DaggerApplicationComponent.builder()
        .application(this)
        .build()


    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector() = applicationInjector

}