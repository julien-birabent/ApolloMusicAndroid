package julienbirabent.apollomusic.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity(
    tableName = "practice",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class PracticeEntity(

    @Expose
    @SerializedName("id")
    @PrimaryKey var id: String,

    @Expose
    @ColumnInfo(name = "profileId")
    var profileId: String,

    @Expose
    @SerializedName("userNotes")
    var userNotes: String?,

    @Expose
    @SerializedName("datePractice")
    var date: Date?,

    @ColumnInfo(name = "pending")
    var pendingForSyncWithServer: Boolean
)
