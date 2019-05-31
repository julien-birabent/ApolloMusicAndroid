package julienbirabent.apollomusic.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import julienbirabent.apollomusic.data.local.model.User

class GoogleUserAdapter (private val googleAccount: GoogleSignInAccount): User {

    override val id: String
        get() = googleAccount.id!!
    override val token: String?
        get() = googleAccount.idToken
    override val email: String?
        get() = googleAccount.email
    override val firstName: String?
        get() = googleAccount.givenName
    override val lastName: String?
        get() = googleAccount.familyName
    override val photoUrl: String?
        get() = googleAccount.photoUrl.toString()
    override val loginType: LoginType?
        get() = LoginType.GOOGLE
}