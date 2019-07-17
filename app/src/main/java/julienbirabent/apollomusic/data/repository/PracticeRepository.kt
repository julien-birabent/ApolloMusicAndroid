package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import julienbirabent.apollomusic.Utils.DefaultLiveData
import julienbirabent.apollomusic.data.api.services.ObjectiveAPI
import julienbirabent.apollomusic.data.api.services.PracticeAPI
import julienbirabent.apollomusic.data.local.dao.ObjectiveCriteriaJoinDao
import julienbirabent.apollomusic.data.local.dao.ObjectiveDao
import julienbirabent.apollomusic.data.local.dao.ObjectiveExerciseJoinDao
import julienbirabent.apollomusic.data.local.dao.PracticeDao
import julienbirabent.apollomusic.data.local.entities.ObjectiveCriteriaJoin
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveExerciseJoin
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import julienbirabent.apollomusic.data.local.model.ObjectiveBundle
import julienbirabent.apollomusic.extensions.add
import julienbirabent.apollomusic.extensions.dbExec
import julienbirabent.apollomusic.extensions.deepCopyOf
import julienbirabent.apollomusic.extensions.set
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeRepository @Inject constructor(
    private val userRepo: UserRepository,
    private val practiceAPI: PracticeAPI,
    private val objAPI: ObjectiveAPI,
    private val practiceDao: PracticeDao,
    private val objectiveDao: ObjectiveDao,
    private val objectiveCriteriaJoinDao: ObjectiveCriteriaJoinDao,
    private val objectiveExerciseJoinDao: ObjectiveExerciseJoinDao
) : BaseRepository() {


    /*
    * Cette méthode est totalement à refaire car elle n'est pas implémenter selon les guidelines de Rx.
    * On a des callbacks imbriquées ce qui est peu pratique et ilisible. Faire la job pour le moment mais si je jdevais
    * refactor le code, c'est ici que je commencerais.
    * */
    @SuppressLint("CheckResult")
    fun createPractices(
        dates: List<Date>,
        objectiveBundle: List<ObjectiveBundle>,
        notes: String?
    ): LiveData<List<Boolean>> {

        val results = MutableLiveData<List<Boolean>>().apply { value = mutableListOf() }
        val practices = mutableListOf<PracticeEntity>().apply {
            dates.forEach {
                this.add(PracticeEntity(userRepo.getLoggedUserId(), notes, it))
                results.add(false)
            }
        }
        for (i in 0 until practices.size) {
            val practice = practices[i]
            practiceAPI.postPractice(practice.date, practice.userNotes, practice.profileId)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .flatMap {
                    practice.id = it.body()?.id

                    dbExec {
                        practiceDao.insert(practice)
                        Log.d("create practice", "inserting practice : $practice")
                    }

                    val objs = deepCopyOf(objectiveBundle)
                    val objCalls = mutableListOf<Single<Response<ObjectiveEntity>>>().apply {
                        objs.forEach { objBundle ->
                            objBundle.obj.practiceId = practice.id
                            this.add(
                                objAPI.postObjective(
                                    objBundle.obj.objective,
                                    objBundle.obj.targetPracticeTime,
                                    objBundle.obj.tempoHistory,
                                    objBundle.obj.tempoBase,
                                    objBundle.obj.practiceId
                                ).subscribeOn(scheduler.io())
                            )
                        }
                    }
                    Single.zip(objCalls) { objects ->
                        Log.d("create practice", "zip objective call")
                        for (bundleIndex in 0 until objs.size) {
                            var bundle = objs[bundleIndex]
                            var responseBody = objects[bundleIndex]
                            bundle.obj.id = (responseBody as Response<ObjectiveEntity>).body()?.id
                            dbExec {
                                objectiveDao.insert(bundle.obj)
                                Log.d("create practice", "inserting objective : $bundle.obj")
                            }
                            val objId = bundle.obj.id
                            val criteriaId = bundle.crit?.id
                            val exerciseId = bundle.ex?.id
                            Single.zip(
                                objAPI.postObjectiveCriteriaJoin(objId.toString(), objId, criteriaId),
                                objAPI.postObjectiveExerciseJoin(objId.toString(), exerciseId, objId),
                                BiFunction<Response<ObjectiveCriteriaJoin>, Response<ObjectiveExerciseJoin>, Unit> { objCriteriaJoin: Response<ObjectiveCriteriaJoin>, objExerciseJoin: Response<ObjectiveExerciseJoin> ->
                                    Log.d("create practice", "zip join call")
                                    dbExec {
                                        objCriteriaJoin.body()?.let { it -> objectiveCriteriaJoinDao.insert(it) }
                                        objExerciseJoin.body()?.let { it -> objectiveExerciseJoinDao.insert(it) }
                                        Log.d("create practice", "inserting ObjectiveCriteriaJoin : $objCriteriaJoin")
                                        Log.d("create practice", "inserting ObjectiveExerciseJoin : $objExerciseJoin")
                                        results.set(i, true)
                                    }
                                }
                            ).observeOn(scheduler.ui()).subscribeOn(scheduler.io())
                                .observeOn(scheduler.ui()).subscribeOn(scheduler.io()).subscribe({
                                }, {
                                    results.set(i, false)
                                })
                        }
                    }.doOnError {
                        results.set(i, false)
                    }
                }.subscribe({}, {
                    results.set(i, false)
                })
        }
        return results
    }

    fun getPracticeList(): LiveData<List<PracticeEntity>> {

        val userId = userRepo.getLoggedUserId()?.toInt()
        return if (userId != null) {
            practiceDao.findPracticeForUser(userId)
        } else return DefaultLiveData.create(emptyList())

    }

    fun fetchPracticeList(): Single<List<PracticeEntity>> {
        return userRepo.getLoggedUser()
            .observeOn(scheduler.io())
            .flatMapSingle {
                practiceAPI.getUserPractices(it.id, it.token?.token)
            }
            .firstOrError()
            .retry(1)
            .flatMap {
                Single.just(it.body() ?: emptyList())
            }
            .onErrorReturn { emptyList() }
    }
}