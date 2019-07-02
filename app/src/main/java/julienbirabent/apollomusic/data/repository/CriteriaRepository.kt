package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Observable
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
            .flatMap { getCriterias(userRepo.getLoggedUserId().toString()) }
            .subscribe({
                Log.d(
                    CriteriaRepository::class.simpleName,
                    "Fetching criteria on connection gained ${it.size}"
                )
            }, {
                Log.e(CriteriaRepository::class.simpleName, "An error happened : " + it.message)
            })
        )
    }

    fun getCriterias(profileId: String): Observable<List<CriteriaEntity>> {
        return Observable.concatArray(
            getCriteriaFromDb(profileId),
            getCriteriaFromServer(profileId)
        )
    }

    private fun filterUserCriteria(userId: String, criteriaList: List<CriteriaEntity>): List<CriteriaEntity> {
        return criteriaList.filter {
            it.profileId.toString() == userId || it.profileId == appConstants.adminProfileId()
        }
    }

    @SuppressLint("CheckResult")
    fun getCriteriaList(profileId: String): LiveData<List<CriteriaEntity>> {
        return criteriaDao.getCriteriaListByUserLive(profileId.toInt(), appConstants.adminProfileId())
    }

    fun saveCriteria(criteria: String): Single<Response<CriteriaEntity>> {
        return criteriaAPI.postCriteria(criteria, userRepo.getLoggedUserId()?.toInt())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.io())
            .doOnSuccess { response ->
                if (response.isSuccessful) {
                    response.body()?.let { storeCriteriaInDb(listOf(it)) }
                }
            }
            .doOnError {
                Log.e("Persist criteria call", "An error happened : " + it.message)
            }
    }

    private fun getCriteriaFromDb(profileId: String): Observable<List<CriteriaEntity>> {
        return criteriaDao.getCriteriaByUser(profileId.toInt(), appConstants.adminProfileId())
            .filter { it.isNotEmpty() }
            .doOnNext {
                Log.d(CriteriaRepository::class.simpleName, "Dispatching ${it.size} criteria from DB...")
            }
    }

    private fun getCriteriaFromServer(profileId: String): Observable<List<CriteriaEntity>> {
        return criteriaAPI.getAllCriterias()
            .map { it.body()!! }
            .doOnSuccess {
                Log.d(CriteriaRepository::class.simpleName, "Dispatching ${it.size} criteria from API...")
                storeCriteriaInDb(filterUserCriteria(profileId, it))
            }.toObservable()
    }

    @SuppressLint("CheckResult")
    private fun storeCriteriaInDb(criterias: List<CriteriaEntity>) {
        Observable.fromCallable { criteriaDao.insert(*criterias.toTypedArray()) }
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.io())
            .subscribe {
                Log.d(CriteriaRepository::class.simpleName, "Inserted ${criterias.size} criterias from API in DB...")
            }
    }
}
