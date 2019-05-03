package julienbirabent.apollomusic.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import julienbirabent.apollomusic.ApolloMusicApplication
import julienbirabent.apollomusic.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, ViewModelModule::class]
)
interface ApplicationComponent : AndroidInjector<ApolloMusicApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ApolloMusicApplication): Builder

        fun build(): ApplicationComponent
    }

}