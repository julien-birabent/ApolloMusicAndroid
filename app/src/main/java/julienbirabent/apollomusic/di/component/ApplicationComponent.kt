package julienbirabent.apollomusic.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import julienbirabent.apollomusic.Utils.StateLiveData
import julienbirabent.apollomusic.app.ApolloMusicApplication
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.repository.BaseRepository
import julienbirabent.apollomusic.di.module.ActivityBuilderModule
import julienbirabent.apollomusic.di.module.AppModule
import julienbirabent.apollomusic.di.module.DataModule
import julienbirabent.apollomusic.di.module.ViewModelModule
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

    fun inject(repository: BaseRepository)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appConstants(appConstants: AppConstants): Builder

        fun build(): ApplicationComponent
    }


}