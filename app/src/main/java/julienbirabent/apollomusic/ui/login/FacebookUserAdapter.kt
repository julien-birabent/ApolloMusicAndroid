package julienbirabent.apollomusic.ui.login

import com.facebook.AccessToken
import com.facebook.Profile
import julienbirabent.apollomusic.data.local.model.User

class FacebookUserAdapter(private val fbToken: AccessToken, private val profile: Profile, private val fbMail: String) :
    User {

    override val id: String
        get() = profile.id
    override val token: String?
        get() = fbToken.token
    override val email: String?
        get() = fbMail
    override val firstName: String?
        get() = profile.firstName
    override val lastName: String?
        get() = profile.lastName
    override val photoUrl: String?
        get() = "https://graph.facebook.com/$id/picture?type=large"
    override val loginType: LoginType?
        get() = LoginType.FACEBOOK
}