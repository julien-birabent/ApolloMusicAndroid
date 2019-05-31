package julienbirabent.apollomusic.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Single
import julienbirabent.apollomusic.Utils.AbsentLiveData
import julienbirabent.apollomusic.data.api.services.UserAPI
import julienbirabent.apollomusic.data.local.dao.UserDao
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.local.model.User
import retrofit2.Response
import java.io.IOException
import java.security.InvalidParameterException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userAPI: UserAPI, private val userDao: UserDao, private val sharedPreferences: SharedPreferences
) : BaseRepository() {

    internal companion object {
        const val key_user_id = "key_user_id"
        const val key_server_token = "key_server_token"
    }

    fun login(user: User): Single<Response<UserEntity>> {
        return with(userAPI.login(user.token, user.loginType?.name?.toLowerCase())) {
            subscribeOn(scheduler.io())
                .doOnSuccess() {
                    Log.d("UserRepo", "Login success")
                    appExecutors.diskIO().execute {
                        if (it.isSuccessful && it.body() != null) {
                            it.body()?.let { userEntity ->
                                userEntity.loginType = user.loginType!!
                                userEntity.userName = user.firstName + " " + user.lastName
                                userEntity.photoUrl = user.photoUrl
                                userEntity.externalId = user.id
                                userEntity.email = userEntity.email
                                userDao.upsert(userEntity).also {
                                    try{
                                        invalidateSession()
                                        if (it != null) {
                                            setUserIdAndToken(it, userEntity.token.token)
                                        }
                                    }catch (e: InvalidParameterException){
                                        //TODO(handle error)
                                    }
                                }
                            }
                        }
                    }
                }
                .observeOn(scheduler.ui())
                .doOnError {
                    if(it is IOException){
                        Log.d("UserRepo", "Login error (IOException) : " + it.message)
                    }
                    Log.d("UserRepo", "Login error : " + it.message)
                }
        }
    }

    fun getCurrentLoggedUser(): LiveData<UserEntity> {
        val userId = getLoggedUserId()
        return if (userId != null) {
            userDao.getUserWithId(userId)
        } else {
            AbsentLiveData.create()
        }
    }

    /**
     * When user is logged in, we use this method to keep track of which user in the db is logged id
     */
    private fun setUserIdAndToken(id: String, token: String) {
        with(sharedPreferences.edit()) {
            putString(key_user_id, id)
            putString(key_server_token, token)
            apply()
        }
    }

    private fun getLoggedUserId(): String? {
        return sharedPreferences.getString(key_user_id, null)
    }

    private fun getServerToken(): String? {
        return sharedPreferences.getString(key_server_token, null)
    }


    /**
     * When we log out a user, we clear the id in shared preferences
     */
    private fun invalidateSession() {
        with(sharedPreferences.edit()) {
            putString(key_user_id, null)
            putString(key_server_token, null)
            apply()
        }
    }
}