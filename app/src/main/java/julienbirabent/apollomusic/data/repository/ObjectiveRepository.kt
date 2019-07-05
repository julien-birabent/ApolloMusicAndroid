package julienbirabent.apollomusic.data.repository

import android.util.Log
import julienbirabent.apollomusic.data.api.services.ObjectiveAPI
import julienbirabent.apollomusic.data.local.dao.ObjectiveDao
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectiveRepository @Inject constructor(
    private val objectiveAPI: ObjectiveAPI,
    private val objectiveDao: ObjectiveDao
) : BaseRepository() {

    companion object {
        private val pendingObjectives: MutableMap<ObjectiveEntity, Pair<CriteriaEntity?, ExerciseEntity?>> =
            mutableMapOf()

        fun addPendingObj(obj: ObjectiveEntity, associatedEntities: Pair<CriteriaEntity?, ExerciseEntity?>) {
            pendingObjectives[obj] = associatedEntities
            Log.d("ObjectiveRepository", "add pending obj : $obj")
        }

        fun removePendingObj(obj: ObjectiveEntity) {
            pendingObjectives.remove(obj)
        }

        fun resetPendingObj() {
            pendingObjectives.clear()
            Log.d("ObjectiveRepository", "clear   (${pendingObjectives.size}) pending objs")
        }

        fun getPendingObjList(): MutableList<ObjectiveEntity> {
            return pendingObjectives.keys.toMutableList()
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
        var objectiveToCreate = ObjectiveEntity().apply {
            objective = objectiveTitle
            tempoHistory = targetTempo.toString()
            targetPracticeTime = practiceTime
        }
        addPendingObj(objectiveToCreate, Pair(criteriaEntity, exerciseEntity))
    }

    fun getPracticeCreationObjList(): MutableList<ObjectiveEntity> {
        return getPendingObjList()
    }
}