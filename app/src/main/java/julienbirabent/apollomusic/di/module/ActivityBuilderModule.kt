package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleActivity
import julienbirabent.apollomusic.ui.example.ExampleFragmentProvider
import julienbirabent.apollomusic.ui.home.HomeActivity
import julienbirabent.apollomusic.ui.login.LoginActivity
import julienbirabent.apollomusic.ui.splash.SplashScreenActivity

@Module(includes = [DataModule::class])
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [ExampleFragmentProvider::class])
    abstract fun bindExampleActivity(): ExampleActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashScreenActivity

    @ContributesAndroidInjector(modules = [FragmentProvider::class])
    abstract fun bindHomeActivity(): HomeActivity
}
