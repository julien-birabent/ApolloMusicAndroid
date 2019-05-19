package julienbirabent.apollomusic.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "users")
class User {

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null

    @field:SerializedName("id")
    @PrimaryKey
    var id: Long? = null

    @field:SerializedName("name")
    var name: String? = null

    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null
}
