package julienbirabent.apollomusic.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import julienbirabent.apollomusic.ApolloMusicApplication
import julienbirabent.apollomusic.di.module.ActivityBuilderModule
import julienbirabent.apollomusic.di.module.ViewModelModule
import julienbirabent.apollomusic.ui.example.ExampleFragmentModule
import javax.inject.Singleton


@Singleton
@Component(
    modules =
    [AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class,
        ExampleFragmentModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: ApolloMusicApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }


}