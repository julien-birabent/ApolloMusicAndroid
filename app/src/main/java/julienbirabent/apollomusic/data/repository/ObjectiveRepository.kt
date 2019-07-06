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
        private val pendingObjectives: MutableLiveData<List<ObjectiveBundle>> = MutableLiveData()

        init {
            pendingObjectives.value = mutableListOf()
        }

        fun addPendingObj(objbundle: ObjectiveBundle) {
            pendingObjectives.add(objbundle)
            Log.d("ObjectiveRepository", "add pending obj : $objbundle")
        }

        fun removePendingObj(obj: ObjectiveBundle) {
            pendingObjectives.remove(obj)
            Log.d("ObjectiveRepository", "remove pending obj : $obj")
        }

        fun resetPendingObj() {
            pendingObjectives.clear()
            Log.d("ObjectiveRepository", "clear   (${pendingObjectives.value?.size}) pending objs")
        }
    }

    init {
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

    fun getPracticeCreationObjList(): MutableLiveData<List<ObjectiveBundle>> {
        return pendingObjectives
    }

    fun deletePendingObj(obj: ObjectiveBundle) {
        removePendingObj(obj)
    }
}