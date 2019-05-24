package julienbirabent.apollomusic.ui.login

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.local.model.User
import julienbirabent.apollomusic.data.repository.UserRepository
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(private val userRepo : UserRepository) :
    BaseViewModel<LoginNavigator>(){

    val user : MutableLiveData<UserEntity> by lazy {
        MutableLiveData<UserEntity>()
    }

    fun signIn(user : User){

        // Check si user exist

        // If user exist ->
    }

    fun signOut(){

    }
    

}