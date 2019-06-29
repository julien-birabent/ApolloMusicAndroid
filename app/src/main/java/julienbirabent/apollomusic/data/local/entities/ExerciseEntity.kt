package julienbirabent.apollomusic.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import julienbirabent.apollomusic.data.local.Difficulty

@Entity(
    tableName = "exercise",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExerciseEntity(

    @Expose
    @SerializedName("id")
    @PrimaryKey var id: Int,

    @Expose
    @SerializedName("profileId")
    var profileId: String,

    @Expose
    @SerializedName("title")
    var title: String,

    @Expose
    @SerializedName("description")
    var description: String,

    @Expose
    @SerializedName("links")
    var links: List<String>?,

    @Expose
    @SerializedName("tempoBase")
    var tempoBase: Int,

    @Expose
    @SerializedName("difficulty")
    var difficulty: Difficulty

    /*@Expose
    @SerializedName("tablature")
    var tablature: Difficulty*/
)