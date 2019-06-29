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
        compositeDisposable.add(connectionAvailableEmitter()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.io())
            .subscribe { connectionAvailable ->
                userRepo.getLoggedUserId()?.let { userId ->
                    val pendingCriterias = criteriaDao.getAllPendingCriteriasByUser(userId)

                    pendingCriterias.forEach { entity ->
                        persistCriteriaOnServerCall(entity)
                            .subscribeOn(scheduler.computation())
                            .observeOn(scheduler.io())
                            .subscribe({ response ->
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

                    fetchCriteriaList(userId)
                        .subscribeOn(scheduler.io())
                        .observeOn(scheduler.io())
                        .subscribe({ response ->
                            if (response.isSuccessful && response.body() != null) {
                                response.body()?.let { criteriaList ->
                                    val userCriteria = filterUserCriteria(userId, criteriaList)
                                    userCriteria.forEach {
                                        it.pendingForSync = false
                                        criteriaDao.insertOrUpdate(it)
                                    }
                                }
                            }
                        }, {
                            Log.e("CriteriaRepository", it.message)
                        })
                }
            })
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

    fun saveCriteria(criteria: String) {
        var criteriaEntity = createCriteria(criteria)

        if (criteriaEntity != null) {
            appExecutors.diskIO().execute {
                criteriaEntity?.let {
                    val id = criteriaDao.insert(it)
                    criteriaEntity = criteriaDao.getCriteriaByInternalId(id)

                    criteriaEntity?.let { entity ->
                        persistCriteriaOnServerCall(entity).subscribe({ response ->
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

    private fun persistCriteriaOnServerCall(entity: CriteriaEntity): Single<Response<CriteriaEntity>> {
        return criteriaAPI.postCriteria(criteria = entity.criteria, profileId = entity.profileId)
            .subscribeOn(scheduler.io())
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
