package julienbirabent.apollomusic.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "criteria",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )])
data class CriteriaEntity(

    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true) var id: Int,

    @Expose
    @SerializedName("profileId")
    var profileId: Int,

    @Expose
    @SerializedName("criteria")
    var criteria: String
)