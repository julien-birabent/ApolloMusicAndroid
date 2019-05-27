package julienbirabent.apollomusic.ui.login

import android.util.Log
import io.reactivex.functions.Consumer
import julienbirabent.apollomusic.data.local.model.User
import julienbirabent.apollomusic.data.repository.UserRepository
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.ui.base.BaseViewModel
import org.reactivestreams.Subscriber
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

    fun login(user: User) {
        userRepo.login(user)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe({
                Log.d("LoginViewModel", "Login success")
                navigator.signInSuccessful()
            }, {
                Log.d("LoginViewModel", "Login error")
                navigator.signInError()
            })
    }

    fun signOut() {

    }


}