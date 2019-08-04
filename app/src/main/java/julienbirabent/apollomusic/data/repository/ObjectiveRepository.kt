package julienbirabent.apollomusic.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import julienbirabent.apollomusic.data.local.dao.*
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity
import julienbirabent.apollomusic.data.local.model.ObjectiveBundle
import julienbirabent.apollomusic.extensions.add
import julienbirabent.apollomusic.extensions.clear
import julienbirabent.apollomusic.extensions.remove
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectiveRepository @Inject constructor(
    private val objectiveDao: ObjectiveDao,
    private val objCriteriaJoinDao: ObjectiveCriteriaJoinDao,
    private val exerciseDao: ExerciseDao,
    private val criteriaDao: CriteriaDao,
    val objExerciseJoinDao: ObjectiveExerciseJoinDao
) : BaseRepository() {

    companion object {
        private val pendingObjectivesLive: MutableLiveData<List<ObjectiveBundle>> = MutableLiveData()
        private val pendingObjList: MutableList<ObjectiveBundle> = mutableListOf()

        init {
            pendingObjectivesLive.value = mutableListOf()
        }

        fun addPendingObj(objbundle: ObjectiveBundle) {
            pendingObjectivesLive.add(objbundle)
            pendingObjList.add(objbundle)
            Log.d("ObjectiveRepository", "add pending obj : $objbundle")
        }

        fun removePendingObj(obj: ObjectiveBundle) {
            pendingObjectivesLive.remove(obj)
            pendingObjList.remove(obj)
            Log.d("ObjectiveRepository", "remove pending obj : $obj")
        }

        fun resetPendingObj() {
            pendingObjectivesLive.clear()
            pendingObjList.clear()
            Log.d("ObjectiveRepository", "clear   (${pendingObjectivesLive.value?.size}) pending objs")
        }
    }

    init {
        resetPendingObj()
    }

    fun getObjectiveBundle(objId: Int): Observable<ObjectiveBundle> {
        return Observable.fromCallable {
            val obj = objectiveDao.findObjective(objId)
            val criteriaEntity = objCriteriaJoinDao.getCriteriaIdByObjId(obj.id)
                .let { criteriaDao.getCriteriaById(it?.criteriaId?.toInt()) }
            val exerciseEntity = objExerciseJoinDao.getExerciseIdByObjId(obj.id).let {
                if (it?.exerciseId != null) {
                    it.exerciseId?.toInt()?.let { exerciseId -> exerciseDao.getExerciseById(exerciseId) }
                } else null
            }

            ObjectiveBundle(obj, exerciseEntity, criteriaEntity)
        }.observeOn(scheduler.ui()).subscribeOn(scheduler.io())
    }

    fun getObjectiveBundleList(practiceId: Int): Observable<MutableList<ObjectiveBundle>> {
        return Observable.fromCallable { objectiveDao.findObjectiveListWithPracticeId(practiceId) }
            .observeOn(scheduler.io())
            .subscribeOn(scheduler.io())
            .flatMap { Observable.fromIterable(it) }
            .flatMap { getObjectiveBundle(it.id) }
            .toList()
            .toObservable()
    }

    fun resetObjCache() {
        resetPendingObj()
    }

    fun createObjective(
        objectiveTitle: String?,
        practiceTime: Int?,
        targetTempo: Int?,
        exerciseEntity: ExerciseEntity? = null,
        criteriaEntity: CriteriaEntity? = null
    ) {
        val objectiveToCreate = ObjectiveEntity().apply {
            objective = objectiveTitle
            tempoBase = targetTempo
            targetPracticeTime = practiceTime
        }
        addPendingObj(ObjectiveBundle(objectiveToCreate, exerciseEntity, criteriaEntity))
    }

    fun getPracticeCreationObjListLive(): MutableLiveData<List<ObjectiveBundle>> {
        return pendingObjectivesLive
    }

    fun getPracticeCreationObjectiveList(): List<ObjectiveBundle> = pendingObjList

    fun deletePendingObj(obj: ObjectiveBundle) {
        removePendingObj(obj)
    }
}