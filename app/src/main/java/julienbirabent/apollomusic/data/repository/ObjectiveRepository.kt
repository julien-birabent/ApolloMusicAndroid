package julienbirabent.apollomusic.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.api.services.ObjectiveAPI
import julienbirabent.apollomusic.data.local.dao.ObjectiveDao
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
    private val objectiveAPI: ObjectiveAPI,
    private val objectiveDao: ObjectiveDao
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

    fun resetObjCache(){
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