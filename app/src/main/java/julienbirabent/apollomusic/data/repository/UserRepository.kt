package julienbirabent.apollomusic.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import julienbirabent.apollomusic.Utils.AbsentLiveData
import julienbirabent.apollomusic.Utils.asSingleEvent
import julienbirabent.apollomusic.data.api.network.ApiResponse
import julienbirabent.apollomusic.data.api.network.NetworkBoundResource
import julienbirabent.apollomusic.data.api.network.Resource
import julienbirabent.apollomusic.data.api.services.UserAPI
import julienbirabent.apollomusic.data.local.dao.UserDao
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.local.model.User
import retrofit2.Response
import java.security.InvalidParameterException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userAPI: UserAPI, private val userDao: UserDao, private val sharedPreferences: SharedPreferences
) : BaseRepository() {

    internal companion object {
        val key_user_id = "key_user_id"
    }

    fun login(loginType: String): Observable<Response<UserEntity>> {
        return userAPI.login(loginType)
    }

    fun getCurrentLoggedUser() : LiveData<UserEntity> {
        val userId = getLoggedUserId()
        return if(userId != null){
            userDao.getUserWithId(userId)
        }else{
            AbsentLiveData.create()
        }
    }

    fun getUser(user: User): LiveData<Resource<UserEntity>> {
        return object : NetworkBoundResource<UserEntity, UserEntity>(appExecutors) {
            override fun saveCallResult(item: UserEntity) {
                with(item) {
                    /**
                     * The server response should provide a UserEntity with username, email and id filled
                     */
                    /*loginType = user.loginType?.name
                    userName = user.firstName
                    photoUrl = user.photoUrl*/
                }
                userDao.insert(item)
            }

            override fun shouldFetch(data: UserEntity?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<UserEntity> {
                return userDao.getUserWithId(user.id).apply {
                    try {
                        this.value?.id?.let { setUserId(it) }
                    } catch (e: InvalidParameterException) {
                        Log.e(this::class.java.name, e.printStackTrace().toString())
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<UserEntity>> {
                return userAPI.getUser(user.id)
            }
        }.asLiveData().asSingleEvent()
    }

    /**
     * When user is logged in, we use this method to keep track of which user in the db is logged id
     */
    private fun setUserId(id: String?) {
        if (id == null) throw InvalidParameterException("cannot set the value null for the user id")
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