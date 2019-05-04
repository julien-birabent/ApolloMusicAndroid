package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleActivity
import julienbirabent.apollomusic.ui.example.ExampleFragment

@Module
abstract class ActivityBuilderModule {

   //example
    @ContributesAndroidInjector(modules = arrayOf(FragmentBuilderModule::class))
    abstract fun bindExampleActivity(): ExampleActivity
}
