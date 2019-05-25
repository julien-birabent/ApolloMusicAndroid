package julienbirabent.apollomusic.ui.login

import julienbirabent.apollomusic.ui.base.UINavigator

interface LoginNavigator: UINavigator {

    fun signInSuccessful()

    fun signInError()
    
}