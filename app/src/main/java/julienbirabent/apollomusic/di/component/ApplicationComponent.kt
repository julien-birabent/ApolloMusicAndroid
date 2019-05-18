package julienbirabent.apollomusic.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import julienbirabent.apollomusic.app.ApolloMusicApplication
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.di.module.*
import javax.inject.Singleton


@Singleton
@Component(
    modules =
    [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class,
        DataModule::class/*,
        DatabaseModule::class,
        ApiModule::class*/
    ]
)
interface ApplicationComponent {

    fun inject(application: ApolloMusicApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appConstants(appConstants: AppConstants): Builder

        fun build(): ApplicationComponent
    }


}