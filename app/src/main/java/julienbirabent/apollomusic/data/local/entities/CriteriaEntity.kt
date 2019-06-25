package julienbirabent.apollomusic.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "criteria",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class CriteriaEntity(

    @Expose
    @SerializedName("id")
    var serverId: Int,

    @Expose
    @SerializedName("profileId")
    var profileId: Int,

    @Expose
    @SerializedName("criteria")
    var criteria: String,

    @ColumnInfo(name = "pending")
    var pendingForSync: Boolean = true
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}