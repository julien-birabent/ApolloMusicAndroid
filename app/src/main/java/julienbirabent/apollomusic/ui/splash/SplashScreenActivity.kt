package julienbirabent.apollomusic.ui.splash

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivitySplashBinding
import julienbirabent.apollomusic.extensions.launchActivity
import julienbirabent.apollomusic.ui.base.BaseActivity
import julienbirabent.apollomusic.ui.home.HomeActivity
import julienbirabent.apollomusic.ui.login.LoginActivity
import julienbirabent.apollomusic.ui.login.LoginType
import javax.inject.Inject


class SplashScreenActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    @Inject
    lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.currentUser.observe(this, Observer {
            if (it != null) {
                when (it.loginType) {
                    LoginType.GOOGLE -> handleGoogleLogin()
                    LoginType.FACEBOOK -> handleFacebookLogin()
                }
            }else goToLoginScreen()
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

    private fun goToLoginScreen() {
        launchActivity<LoginActivity> {}
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun goToHomeScreen() {
        launchActivity<HomeActivity> {}
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    //region Base Activity Implements
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModelClass(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }
    //endregion
}