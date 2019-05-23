package julienbirabent.apollomusic.ui.login

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityLoginBinding
import julienbirabent.apollomusic.ui.base.BaseActivity
import javax.inject.Inject


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),
    LoginNavigator {

    companion object {
        const val RC_SIGN_IN: Int = 666
    }

    @Inject
    lateinit var gsc: GoogleSignInClient

    override fun signIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun signOut() {
        gsc.signOut()
            .addOnCompleteListener(this) {
                //TODO Logout
            }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken


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