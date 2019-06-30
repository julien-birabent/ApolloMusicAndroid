package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity

@Dao
abstract class CriteriaDao : BaseDao<CriteriaEntity> {

    @Query("SELECT * FROM criteria WHERE id = :id LIMIT 1")
    abstract fun getCriteriaById(id: Int): CriteriaEntity?

    @Query("SELECT * FROM criteria WHERE profileId= :profileId OR profileId= :adminProfileId")
    abstract fun getCriteriaListByUserLive(profileId: Int, adminProfileId: Int): LiveData<List<CriteriaEntity>>

    @Query("SELECT * FROM criteria WHERE profileId= :profileId")
    abstract fun getCriteriaByUser(profileId: Int): List<CriteriaEntity>

    @Query("SELECT * FROM criteria")
    abstract fun getAllCriterias(): List<CriteriaEntity>

   /* fun insertOrUpdate(item: CriteriaEntity) {
        val id = getCriteriaById(item.id)
        if (id == null)
            insert(item)
        else
            update(item)
    }*/
}