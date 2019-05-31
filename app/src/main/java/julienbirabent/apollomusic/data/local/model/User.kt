package julienbirabent.apollomusic.data.local.model

import julienbirabent.apollomusic.ui.login.LoginType

interface User{

    val id: String
    val token: String?
    val email: String?
    val firstName: String?
    val lastName: String?
    val photoUrl : String?
    val loginType : LoginType?

}
