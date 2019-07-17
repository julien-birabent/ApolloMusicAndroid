package julienbirabent.apollomusic.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "objective",
    foreignKeys = [ForeignKey(
        entity = PracticeEntity::class,
        parentColumns = ["id"],
        childColumns = ["practiceId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class ObjectiveEntity(

    @Expose
    @SerializedName("id")
    @PrimaryKey var id: Int? = -1,

    @Expose
    @SerializedName("objectif")
    var objective: String? = "" +
            "",

    @Expose
    @SerializedName("targetPracticeTime")
    var targetPracticeTime: Int? = 0,

    @Expose
    @SerializedName("tempoHistory")
    var tempoHistory: String? = "blank",

    @Expose
    @SerializedName("tempoBase")
    var tempoBase: Int? = 0,

    @Expose
    @SerializedName("practiceId")
    @ColumnInfo(name = "practiceId")
    var practiceId: Int? = -1
)
