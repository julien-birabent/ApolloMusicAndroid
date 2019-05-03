package julienbirabent.apollomusic.di.module

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import julienbirabent.apollomusic.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
