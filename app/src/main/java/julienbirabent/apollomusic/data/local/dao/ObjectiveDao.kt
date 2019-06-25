package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity
import julienbirabent.apollomusic.data.local.entities.PracticeEntity

@Dao
abstract class ObjectiveDao : BaseDao<ObjectiveEntity> {

    @Query("SELECT * FROM objective WHERE practiceId=:practiceId")
    abstract fun findObjectiveForPractice(practiceId: Int): LiveData<List<ObjectiveEntity>>

}