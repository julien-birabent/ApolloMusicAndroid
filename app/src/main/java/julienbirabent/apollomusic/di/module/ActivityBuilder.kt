package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleActivity

@Module
abstract class ActivityBuilder {

   //example
    @ContributesAndroidInjector
    abstract fun bindExampleActivity(): ExampleActivity
}
