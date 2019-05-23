package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleActivity
import julienbirabent.apollomusic.ui.example.ExampleFragmentProvider
import julienbirabent.apollomusic.ui.login.LoginActivity

@Module(includes = arrayOf(DataModule::class))
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = arrayOf(ExampleFragmentProvider::class))
    abstract fun bindExampleActivity(): ExampleActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity
}
