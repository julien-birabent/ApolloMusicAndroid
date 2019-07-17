package julienbirabent.apollomusic.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Single
import julienbirabent.apollomusic.Utils.AbsentLiveData
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.api.services.UserAPI
import julienbirabent.apollomusic.data.local.dao.UserDao
import julienbirabent.apollomusic.data.local.entities.Token
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.local.model.User
import julienbirabent.apollomusic.ui.login.LoginType
import retrofit2.Response
import java.io.IOException
import java.security.InvalidParameterException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userAPI: UserAPI, private val userDao: UserDao, private val sharedPreferences: SharedPreferences,
    private val appConstants: AppConstants
) : BaseRepository() {

    internal companion object {
        const val key_user_id = "key_user_id"
        const val key_user_token = "key_user_token"
    }

    init {
        createAdminProfile()
    }

    private fun createAdminProfile() {
        appExecutors.diskIO().execute {
            if (userDao.getUserById(appConstants.adminProfileId().toString()) == null) {
                userDao.upsert(
                    UserEntity(
                        appConstants.adminProfileId().toString(),
                        appConstants.adminProfileId().toString(),
                        Token(),
                        null,
                        "admin",
                        null,
                        LoginType.GOOGLE
                    )
                )
            }
        }
    }

    fun login(user: User): Single<Response<UserEntity>> {
        return with(userAPI.login(user.token, user.loginType?.name?.toLowerCase())) {
            subscribeOn(scheduler.io())
                .doOnSuccess {
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
                                    try {
                                        invalidateSession()
                                        if (it != null) {
                                            setUserIdAndToken(it, userEntity.token.token)
                                        }
                                    } catch (e: InvalidParameterException) {
                                        //TODO(handle error)
                                    }
                                }
                            }
                        }
                    }
                }
                .observeOn(scheduler.ui())
                .doOnError {
                    if (it is IOException) {
                        Log.d("UserRepo", "Login error (IOException) : " + it.message)
                    }
                    Log.d("UserRepo", "Login error : " + it.message)
                }
                .retry(1)
        }
    }

    fun getCurrentLoggedUser(): LiveData<UserEntity> {
        val userId = getLoggedUserId()
        return if (userId != null) {
            userDao.getUserByIdLive(userId)
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
            putString(key_user_token, token)
            apply()
        }
    }

    fun getLoggedUserToken(): String? {
        return sharedPreferences.getString(key_user_token, null)
    }

    fun getLoggedUserId(): String? {
        return sharedPreferences.getString(key_user_id, null)
    }

    fun getLoggedUser(): Observable<UserEntity> {
        val userId = getLoggedUserId()
        return if (userId != null) {
            userDao.getUserByIdObservable(userId)
        } else Observable.empty()
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