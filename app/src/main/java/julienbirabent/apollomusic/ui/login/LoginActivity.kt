package julienbirabent.apollomusic.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityLoginBinding
import julienbirabent.apollomusic.extensions.launchActivity
import julienbirabent.apollomusic.ui.base.BaseActivity
import julienbirabent.apollomusic.ui.home.HomeActivity
import java.util.*
import javax.inject.Inject


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),
    LoginNavigator {

    companion object {
        const val RC_SIGN_IN: Int = 666
    }

    @Inject
    lateinit var fcm: CallbackManager

    @Inject
    lateinit var gsc: GoogleSignInClient

    /**
     * Method called when the viewModel successfully handled the login
     * Do the navigation here
     */
    override fun signInSuccessful() {
        launchActivity<HomeActivity> {}
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun signInError() {
       
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFacebookSignIn()
        setupGoogleSignIn()
    }

    private fun setupGoogleSignIn() {
        binding.googleSignInButton.setOnClickListener {
            val signInIntent = gsc.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun setupFacebookSignIn() {
        LoginManager.getInstance().registerCallback(fcm, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.let {
                    handleFacebookSignInResult(it)
                }
            }

            override fun onCancel() {
                Log.d("Facebook login", "Cancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d("Facebook login", "Error : " + error?.message)
            }
        })
        binding.facebookSignInButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fcm.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleFacebookSignInResult(result: LoginResult) {

        GraphRequest.newMeRequest(result.accessToken) { me, response ->
            if (response.error != null) {
                // handle error
            } else {
                FacebookUserAdapter(
                    result.accessToken,
                    Profile.getCurrentProfile(),
                    me.optString("email")
                ).apply {
                    viewModel.login(this)
                }
                Log.d("Facebook login", "Success")
            }
        }.executeAsync()

    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Valider le token

            if (account != null && account.id != null) {
                with(GoogleSignIn.getLastSignedInAccount(this)) {
                    this?.let {
                        GoogleUserAdapter(it)
                    }
                }.apply {
                    Log.d("Google login", "Success")
                    this?.let { viewModel.login(it) }
                }
            }
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("LoginActivity", "signInResult:failed code=" + e.statusCode)

        }

    }

    //region BaseActivity overrides
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }
//endregion

}