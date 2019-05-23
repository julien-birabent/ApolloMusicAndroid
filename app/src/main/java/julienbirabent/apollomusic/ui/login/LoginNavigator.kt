package julienbirabent.apollomusic.ui.login

import julienbirabent.apollomusic.ui.base.UINavigator

interface LoginNavigator: UINavigator {

    fun signIn()

    fun signOut()
}