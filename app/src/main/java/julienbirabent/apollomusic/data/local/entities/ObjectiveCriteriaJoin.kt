package julienbirabent.apollomusic.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "objective_criteria_join",
    primaryKeys = ["objectiveId", "criteriaId"]/*,
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
    ]*/
)
data class ObjectiveCriteriaJoin(
    @Expose
    @SerializedName("objectiveId")
    var objectiveId: String,

    @Expose
    @SerializedName("criteriaId")
    var criteriaId: String
)