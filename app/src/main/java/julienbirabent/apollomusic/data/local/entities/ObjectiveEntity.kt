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
        onDelete = ForeignKey.CASCADE
    )]
)
data class ObjectiveEntity(

    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true) var id: Int,

    @Expose
    @SerializedName("objectif")
    var objective: String?,

    @Expose
    @SerializedName("targetPracticeTime")
    var targetPracticeTime: String?,

    @Expose
    @SerializedName("practiceId")
    @ColumnInfo(name = "practiceId")
    var practiceId: String?
)
