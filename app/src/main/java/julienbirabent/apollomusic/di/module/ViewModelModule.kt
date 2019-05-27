package julienbirabent.apollomusic.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import julienbirabent.apollomusic.data.repository.ExampleRepository
import julienbirabent.apollomusic.di.annotation.ViewModelKey
import julienbirabent.apollomusic.ui.example.ExampleViewModel
import julienbirabent.apollomusic.ui.home.HomeViewModel
import julienbirabent.apollomusic.ui.login.LoginViewModel
import julienbirabent.apollomusic.ui.splash.SplashViewModel
import julienbirabent.apollomusic.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    abstract fun bindExampleViewModel(viewModel: ExampleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}