package julienbirabent.apollomusic.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "objective_exercise_join",
    primaryKeys = ["objectiveId", "exerciseId"],
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
    ]
)
data class ObjectiveExerciseJoin(
    var objectiveId: String,

    var exerciseId: String
)