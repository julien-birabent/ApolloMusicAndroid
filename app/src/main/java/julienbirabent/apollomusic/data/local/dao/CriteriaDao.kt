package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity

@Dao
abstract class CriteriaDao : BaseDao<CriteriaEntity> {

    @Query("SELECT * FROM criteria WHERE profileId= :profileId OR id= :adminProfileId")
    abstract fun getCriteriaByUserLive(profileId: Int, adminProfileId: Int): LiveData<List<CriteriaEntity>>

    @Query("SELECT * FROM criteria WHERE profileId= :profileId")
    abstract fun getCriteriaByUser(profileId: Int): List<CriteriaEntity>

    @Query("SELECT * FROM criteria")
    abstract fun getAllCriterias(): List<CriteriaEntity>

    @Query("select * from criteria where id = :id")
    abstract fun getCriteriaByInternalId(id: Long): CriteriaEntity

}