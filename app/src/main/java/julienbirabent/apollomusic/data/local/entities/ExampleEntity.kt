package julienbirabent.apollomusic.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "examples")
data class ExampleEntity(

    @PrimaryKey(autoGenerate = true) val id: Int?,
    @Expose
    @SerializedName("started")
    val started: String? = null,
    @Expose
    @SerializedName("uptime")
    val uptime: String? = null

)
