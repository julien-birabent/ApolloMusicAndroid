package julienbirabent.apollomusic.ui.example

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleFragment

@Module
abstract class ExampleFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeExampleFragment(): ExampleFragment
}