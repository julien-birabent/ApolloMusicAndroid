package julienbirabent.apollomusic.ui.login

import android.util.Log
import julienbirabent.apollomusic.data.local.model.User
import julienbirabent.apollomusic.data.repository.UserRepository
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val scheduler: AppSchedulerProvider
) :
    BaseViewModel<LoginNavigator>() {

    fun signIn(user: User) {
        /*userRepo.getUser(user).observeForever {
            when (it.status) {
                Status.SUCCESS -> {
                    setIsLoading(false)
                    navigator.signInSuccessful()
                }
                Status.ERROR -> {
                    navigator.signInError()
                    setIsLoading(false)
                }
                Status.LOADING -> setIsLoading(true)
            }
        }*/
    }

    fun login(loginType: LoginType) {
        userRepo.login(loginType.name.toLowerCase())
            .subscribeOn(scheduler.io())
            .doOnNext {
                Log.d("Login", "Login success")
            }.doOnError {
                Log.d("Login", "Login error")
            }.doOnComplete {
                Log.d("Login", "Login complete")
            }
            .observeOn(scheduler.ui())
            .subscribe()
    }

    fun signOut() {

    }


}