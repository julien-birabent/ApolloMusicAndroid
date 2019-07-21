package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Observable
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity

@Dao
abstract class ExerciseDao : BaseDao<ExerciseEntity> {

    @Query("select * from exercise")
    abstract fun getAllExercises(): Observable<List<ExerciseEntity>>

    @Query("select * from exercise")
    abstract fun getExercises(): List<ExerciseEntity>

    @Query("select * from exercise where id=:exId")
    abstract fun getExerciseById(exId: Int): ExerciseEntity

    @Query("select * from exercise")
    abstract fun getAllExercisesLive(): LiveData<List<ExerciseEntity>>
}
