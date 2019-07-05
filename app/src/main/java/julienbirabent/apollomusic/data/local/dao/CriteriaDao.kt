package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Observable
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity

@Dao
abstract class CriteriaDao : BaseDao<CriteriaEntity> {

    @Query("SELECT * FROM criteria WHERE id = :id LIMIT 1")
    abstract fun getCriteriaById(id: Int): CriteriaEntity?

    @Query("SELECT * FROM criteria WHERE profileId= :profileId OR profileId= :adminProfileId")
    abstract fun getCriteriaListByUserLive(profileId: Int, adminProfileId: Int): LiveData<List<CriteriaEntity>>

    @Query("SELECT * FROM criteria WHERE profileId= :profileId OR profileId= :adminProfileId")
    abstract fun getCriteriaByUser(profileId: Int, adminProfileId: Int): Observable<List<CriteriaEntity>>

    @Query("SELECT * FROM criteria")
    abstract fun getAllCriterias(): List<CriteriaEntity>

}