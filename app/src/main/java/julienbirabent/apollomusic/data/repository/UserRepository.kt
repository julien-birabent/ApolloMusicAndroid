package julienbirabent.apollomusic.data.repository

import julienbirabent.apollomusic.data.api.services.UserAPI
import julienbirabent.apollomusic.data.local.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userAPI: UserAPI, private val userDao: UserDao
) : BaseRepository() {


}