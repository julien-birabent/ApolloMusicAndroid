package julienbirabent.apollomusic.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveCriteriaJoin

@Dao
abstract class ObjectiveCriteriaJoinDao : BaseDao<ObjectiveCriteriaJoin> {

    @Query(
        "SELECT * FROM criteria " +
                "INNER JOIN objective_criteria_join " +
                "ON criteria.id=objective_criteria_join.criteriaId " +
                "WHERE objective_criteria_join.objectiveId=:id"
    )
    abstract fun getCriteriaWithObjectiveId(id: Int): CriteriaEntity

    @Query("select * from objective_criteria_join where objectiveId=:objId")
    abstract fun getCriteriaIdByObjId(objId : Int) :ObjectiveCriteriaJoin
}