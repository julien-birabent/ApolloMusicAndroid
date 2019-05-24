package julienbirabent.apollomusic.ui.login

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.local.model.User
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginNavigator>(){



    val user : MutableLiveData<UserEntity> by lazy {
        MutableLiveData<UserEntity>()
    }

    fun signIn(user : User){

    }

    fun signOut(){

    }
    

}