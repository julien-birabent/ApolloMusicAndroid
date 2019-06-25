package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.PracticeEntity

@Dao
abstract class PracticeDao : BaseDao<PracticeEntity> {

    @Query("SELECT * FROM practice WHERE profileId=:userId")
    abstract fun findPracticeForUser(userId: Int): LiveData<List<PracticeEntity>>

}