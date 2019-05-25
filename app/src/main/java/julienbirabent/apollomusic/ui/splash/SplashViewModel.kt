package julienbirabent.apollomusic.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.repository.UserRepository
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel<SplashNavigator>() {

    val currentUser : LiveData<UserEntity> by lazy {
        userRepository.getCurrentLoggedUser()
    }

}