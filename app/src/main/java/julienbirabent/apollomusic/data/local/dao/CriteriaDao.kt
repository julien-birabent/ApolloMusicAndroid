package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity

@Dao
abstract class CriteriaDao : BaseDao<CriteriaEntity> {

    @Query("SELECT * FROM criteria WHERE serverId = :id LIMIT 1")
    abstract fun getCriteriaByServerId(id: Int): CriteriaEntity?

    @Query("SELECT * FROM criteria WHERE profileId= :profileId OR id= :adminProfileId")
    abstract fun getCriteriaListByUserLive(profileId: Int, adminProfileId: Int): LiveData<List<CriteriaEntity>>

    @Query("SELECT * FROM criteria WHERE profileId= :profileId")
    abstract fun getCriteriaByUser(profileId: Int): List<CriteriaEntity>

    @Query("SELECT * FROM criteria")
    abstract fun getAllCriterias(): List<CriteriaEntity>

    @Query("select * from criteria where profileId = :userId and pending = 1")
    abstract fun getAllPendingCriteriasByUser(userId: String): List<CriteriaEntity>

    @Query("select * from criteria where id = :id")
    abstract fun getCriteriaByInternalId(id: Long): CriteriaEntity

    fun insertOrUpdate(item: CriteriaEntity) {
        val id = getCriteriaByServerId(item.serverId)
        if (id == null)
            insert(item)
        else
            update(item)
    }
}