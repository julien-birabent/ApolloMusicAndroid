package julienbirabent.apollomusic.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Single
import julienbirabent.apollomusic.Utils.ConnectionAvailableEmitter.connectionAvailableEmitter
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.api.services.CriteriaAPI
import julienbirabent.apollomusic.data.local.dao.CriteriaDao
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.SchedulerProvider
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CriteriaRepository @Inject constructor(
    private val userRepo: UserRepository,
    private val criteriaDao: CriteriaDao,
    private val criteriaAPI: CriteriaAPI,
    private val appConstants: AppConstants,
    private val appExecutors: AppExecutors,
    private val scheduler: SchedulerProvider
) {

    val currentUserLiveData: LiveData<UserEntity> = userRepo.getCurrentLoggedUser()

    init {
        connectionAvailableEmitter()?.subscribe {
            //fetchCriteriaList(userRepo.getLoggedUserId())
        }

    }

    private fun fetchCriteriaList(loggedUserId: String?): Single<List<CriteriaEntity>> {
        //TODO() call server pour fetch les critere du user connecté
        // Mettre à jours la BD avec les criteres
        return Single.just(null)

    }

    fun getCriteriaList(profileId: String): LiveData<List<CriteriaEntity>> {
        return criteriaDao.getCriteriaListByUserLive(
            profileId.toInt(),
            adminProfileId = appConstants.adminProfileI()
        )
    }

    fun saveCriteria(criteria: String) {
        var criteriaEntity = createCriteria(criteria)

        if (criteriaEntity != null) {
            appExecutors.diskIO().execute {
                criteriaEntity?.let {
                    val id = criteriaDao.insert(it)
                    criteriaEntity = criteriaDao.getCriteriaByInternalId(id)

                    criteriaEntity?.let { entity ->
                        persistCriteriaOnServer(entity).subscribe({ response ->
                            if (response.isSuccessful && response.body() != null) {
                                response.body()?.let { body ->
                                    entity.pendingForSync = false
                                    entity.serverId = body.serverId
                                    criteriaDao.update(entity)
                                }
                            }
                        }, { throwable ->
                            Log.e("CriteriaRepository", throwable.message)
                        })
                    }
                }
            }
        }
    }

    private fun persistCriteriaOnServer(entity: CriteriaEntity): Single<Response<CriteriaEntity>> {
        return criteriaAPI.postCriteria(criteria = entity.criteria, profileId = entity.profileId)
            .observeOn(scheduler.io())
    }

    private fun createCriteria(criteria: String): CriteriaEntity? {
        return currentUserLiveData.value?.let {
            CriteriaEntity(
                criteria = criteria,
                serverId = -1,
                profileId = it.id.toInt(),
                pendingForSync = true
            )
        }
    }

}
