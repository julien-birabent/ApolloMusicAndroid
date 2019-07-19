package julienbirabent.apollomusic.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "objective_exercise_join",
    primaryKeys = ["objectiveId", "exerciseId"]/*,
    foreignKeys = [
        ForeignKey(
            entity = ObjectiveEntity::class,
            parentColumns = ["id"],
            childColumns = ["objectiveId"]
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"]
        )
    ]*/
)
data class ObjectiveExerciseJoin(
    @Expose
    @SerializedName("objectiveId")
    var objectiveId: String,

    @Expose
    @SerializedName("exerciceId")
    var exerciseId: String
)