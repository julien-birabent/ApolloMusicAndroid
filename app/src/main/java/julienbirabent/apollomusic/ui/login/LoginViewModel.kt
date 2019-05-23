package julienbirabent.apollomusic.ui.login

import android.util.Log
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginNavigator>(){

    fun signIn(){
        Log.d("SignInCall VM", "Trying to signin")
        navigator.signIn()
    }

    fun signOut(){
        navigator.signOut()
    }

}