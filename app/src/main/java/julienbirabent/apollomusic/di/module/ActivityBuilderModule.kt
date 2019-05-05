package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleActivity
import julienbirabent.apollomusic.ui.example.ExampleFragmentModule

@Module
abstract class ActivityBuilderModule {

    //example
    @ContributesAndroidInjector(modules = arrayOf(ExampleFragmentModule::class))
    abstract fun bindExampleActivity(): ExampleActivity
}
