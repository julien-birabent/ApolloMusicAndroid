package julienbirabent.apollomusic.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Single
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.api.services.CriteriaAPI
import julienbirabent.apollomusic.data.local.dao.CriteriaDao
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.UserEntity
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CriteriaRepository @Inject constructor(
    private val userRepo: UserRepository,
    private val criteriaDao: CriteriaDao,
    private val criteriaAPI: CriteriaAPI,
    private val appConstants: AppConstants
) : BaseRepository() {

    val currentUserLiveData: LiveData<UserEntity> = userRepo.getCurrentLoggedUser()

    init {

    }

    private fun filterUserCriteria(userId: String, criteriaList: List<CriteriaEntity>): List<CriteriaEntity> {
        return criteriaList.filter {
            it.profileId.toString() == userId
        }
    }

    private fun fetchCriteriaList(loggedUserId: String): Single<Response<List<CriteriaEntity>>> {
        return criteriaAPI.getAllCriterias()
    }

    fun getCriteriaList(profileId: String): LiveData<List<CriteriaEntity>> {
        return criteriaDao.getCriteriaListByUserLive(
            profileId.toInt(),
            adminProfileId = appConstants.adminProfileId()
        )
    }

    fun saveCriteria(criteria: String): Single<Response<CriteriaEntity>> {
        return criteriaAPI.postCriteria(criteria, userRepo.getLoggedUserId()?.toInt())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.io())
            .doOnSuccess { response ->
                if (response.isSuccessful) {
                    appExecutors.diskIO().execute {
                        response.body()?.let { criteriaDao.insert(it) }
                    }
                }
            }
            .doOnError {
                Log.e("Persist criteria call", "An error happened : " + it.message)
            }
    }
}
