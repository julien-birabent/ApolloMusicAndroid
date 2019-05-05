package julienbirabent.apollomusic.ui.example

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleFragment

@Module
abstract class ExampleFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeExampleFragment(): ExampleFragment
}