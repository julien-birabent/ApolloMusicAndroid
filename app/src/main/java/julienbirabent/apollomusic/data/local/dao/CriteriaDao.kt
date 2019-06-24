package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity

@Dao
abstract class CriteriaDao : BaseDao<CriteriaEntity> {

    @Query("SELECT * FROM criteria WHERE id= :profileId OR id= :adminProfileId")
    abstract fun getCriteriasByUser(profileId: String, adminProfileId: Int): LiveData<List<CriteriaEntity>>

    @Query("select * from criteria where id = :id")
    abstract fun getCriteriaByInternalId(id: Long): CriteriaEntity

}