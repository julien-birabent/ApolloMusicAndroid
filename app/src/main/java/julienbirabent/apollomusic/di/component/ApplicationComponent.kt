package julienbirabent.apollomusic.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import julienbirabent.apollomusic.app.ApolloMusicApplication
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.di.module.ActivityBuilderModule
import julienbirabent.apollomusic.di.module.AppModule
import julienbirabent.apollomusic.di.module.DatabaseModule
import julienbirabent.apollomusic.di.module.ViewModelModule
import javax.inject.Singleton


@Singleton
@Component(
    modules =
    [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: ApolloMusicApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appConstants(appConstants: AppConstants):Builder

        fun build(): ApplicationComponent
    }


}