package julienbirabent.apollomusic.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "objective_criteria_join",
    primaryKeys = ["objectiveId", "criteriaId"],
    foreignKeys = [
        ForeignKey(
            entity = ObjectiveEntity::class,
            parentColumns = ["id"],
            childColumns = ["objectiveId"]
        ),
        ForeignKey(
            entity = CriteriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["criteriaId"]
        )
    ]
)
data class ObjectiveCriteriaJoin(
    var objectiveId: String,
    var criteriaId: String
)