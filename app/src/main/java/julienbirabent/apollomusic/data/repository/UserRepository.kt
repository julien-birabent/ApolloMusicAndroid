package julienbirabent.apollomusic.data.repository

import android.content.SharedPreferences
import julienbirabent.apollomusic.data.api.services.UserAPI
import julienbirabent.apollomusic.data.local.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userAPI: UserAPI, private val userDao: UserDao, private val sharedPreferences: SharedPreferences
) : BaseRepository() {

    internal companion object {
        val key_user_id = "key_user_id"
    }

    /**
     * When user is logged in, we use this method to keep track of which user in the db is logged id
     */
    private fun setUserId(id: String) {
        with(sharedPreferences.edit()) {
            putString(key_user_id, id)
            apply()
        }
    }

    private fun getLoggedUserId(): String? {
        return sharedPreferences.getString(key_user_id, null)
    }

    /**
     * When we log out a user, we clear the id in shared preferences
     */
    private fun invalidateSession() {
        with(sharedPreferences.edit()) {
            putString(key_user_id, null)
            apply()
        }
    }
}