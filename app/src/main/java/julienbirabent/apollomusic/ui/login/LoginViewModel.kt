package julienbirabent.apollomusic.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.local.entities.User
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginNavigator>(){

    val user : MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    fun signInGoogle(){
        Log.d("SignInCall VM", "Trying to signin")
        navigator.signInGoogle()
    }

    fun signInFacebook(): Unit{
        navigator.signInFacebook()
    }

    fun signOut(){
        navigator.signOut()
    }
    

}