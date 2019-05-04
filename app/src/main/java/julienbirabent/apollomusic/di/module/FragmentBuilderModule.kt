package julienbirabent.apollomusic.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import julienbirabent.apollomusic.ui.example.ExampleFragment

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun contributeExampleFragment(): ExampleFragment
}