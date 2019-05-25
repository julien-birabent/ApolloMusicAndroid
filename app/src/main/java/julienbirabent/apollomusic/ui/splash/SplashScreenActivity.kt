package julienbirabent.apollomusic.ui.splash

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.databinding.ActivitySplashBinding
import julienbirabent.apollomusic.ui.base.BaseActivity
import julienbirabent.apollomusic.ui.login.LoginType
import javax.inject.Inject


class SplashScreenActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    @Inject
    lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        viewModel.currentUser.observe(this, Observer {
            if (it != null) {
                when (it.loginType) {
                    LoginType.GOOGLE -> handleGoogleLogin()
                    LoginType.FACEBOOK -> handleFacebookLogin()
                }
            }
        })
    }

    private fun handleFacebookLogin() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if(isLoggedIn) goToHomeScreen() else goToLoginScreen()
    }

    private fun handleGoogleLogin() {
        gsc.silentSignIn().addOnCompleteListener {
            goToHomeScreen()
        }.addOnFailureListener {
            goToLoginScreen()
        }
    }

    fun goToLoginScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun goToHomeScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //region Base Activity Implements
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return julienbirabent.apollomusic.R.layout.activity_splash
    }

    override fun getViewModelClass(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }
    //endregion
}