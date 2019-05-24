package julienbirabent.apollomusic.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityLoginBinding
import julienbirabent.apollomusic.ui.base.BaseActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.facebookSignInButton.setReadPermissions(Arrays.asList("email", "public_profile"))
        viewDataBinding.facebookSignInButton.registerCallback(fcm, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val accessToken = result?.accessToken
                Log.d("Facebook login", "Success")
            }

            override fun onCancel() {
                Log.d("Facebook login", "Cancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d("Facebook login", "Error : " + error?.message)
            }
        })
    }

    override fun signInGoogle() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun signInFacebook() {

    }

    override fun signOut() {
        gsc.signOut()
            .addOnCompleteListener(this) {
                //TODO Logout
            }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fcm.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken

            // Valider le token

            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto = acct.photoUrl
            }
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("LoginActivity", "signInResult:failed code=" + e.statusCode)

        }

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

}