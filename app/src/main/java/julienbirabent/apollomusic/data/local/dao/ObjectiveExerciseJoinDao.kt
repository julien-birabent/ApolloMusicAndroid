package julienbirabent.apollomusic.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveExerciseJoin

@Dao
abstract class ObjectiveExerciseJoinDao : BaseDao<ObjectiveExerciseJoin> {

    // Does not work for some reason
    @Query("SELECT * FROM exercise INNER JOIN objective_exercise_join ON exercise.id=objective_exercise_join.exerciseId WHERE objective_exercise_join.objectiveId=:id")
    abstract fun getExerciseWithObjectiveId(id: Int): ExerciseEntity

    @Query("select * from objective_exercise_join where objectiveId=:objId")
    abstract fun getExerciseIdByObjId(objId : Int) :ObjectiveExerciseJoin

    @Query("select * from objective_exercise_join")
    abstract fun getAll(): List<ObjectiveExerciseJoin>
}