package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveCriteriaJoin
import julienbirabent.apollomusic.data.local.entities.ObjectiveExerciseJoin

@Dao
abstract class ObjectiveExerciseJoinDao : BaseDao<ObjectiveExerciseJoin> {

    @Query("SELECT * FROM exercise INNER JOIN objective_exercise_join ON exercise.id=objective_exercise_join.exerciseId WHERE objective_exercise_join.objectiveId=:id")
    abstract fun getExerciseWithObjectiveId(id: String): LiveData<ExerciseEntity>
}