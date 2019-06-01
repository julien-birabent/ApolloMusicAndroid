package julienbirabent.apollomusic.ui.example

import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

@Module
abstract class ExampleFragmentProvider {

    @ContributesAndroidInjector(modules = [ExampleFragmentModule::class])
    abstract fun contributeExampleFragment(): ExampleFragment
}