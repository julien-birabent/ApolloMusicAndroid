package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.PracticeEntity

@Dao
abstract class PracticeDao : BaseDao<PracticeEntity> {

    @Query("SELECT * FROM practice WHERE profileId=:userId")
    abstract fun findPracticeForUser(userId: Int): LiveData<List<PracticeEntity>>

    @Query("SELECT * FROM practice WHERE profileId=:userId")
    abstract fun findPractices(userId: String): List<PracticeEntity>

    @Query("SELECT * FROM practice WHERE id=:practiceId")
    abstract fun findPracticeByIdLive(practiceId: Int): LiveData<PracticeEntity>

    @Query("SELECT * FROM practice WHERE id=:practiceId")
    abstract fun findPracticeById(practiceId: Int): PracticeEntity

    fun synchronizeUserPractices(userId: String?, freshPractices: List<PracticeEntity>) : List<PracticeEntity> {
        userId?.let {
            val practices = findPractices(it)
            delete(*practices.toTypedArray())
            insert(*freshPractices.toTypedArray())
        }
        return freshPractices
    }
}