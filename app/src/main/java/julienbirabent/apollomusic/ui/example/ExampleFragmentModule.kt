package julienbirabent.apollomusic.ui.example

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ExampleFragmentModule {

    @Provides
    @Named("FragArgs")
    fun provideExampleArgs() : String {
        return "Im an example"
    }
}