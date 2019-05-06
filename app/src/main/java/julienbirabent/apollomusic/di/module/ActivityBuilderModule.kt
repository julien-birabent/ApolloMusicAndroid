package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleActivity
import julienbirabent.apollomusic.ui.example.ExampleFragmentProvider

@Module
abstract class ActivityBuilderModule {

    //example
    @ContributesAndroidInjector(modules = arrayOf(ExampleFragmentProvider::class))
    abstract fun bindExampleActivity(): ExampleActivity
}
