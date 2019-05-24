package julienbirabent.apollomusic.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey var id: String,

    // for keeping the id given by facebook or google
    var externalId: String,

    @Expose
    @ColumnInfo var email: String?,

    @Expose
    @SerializedName("username")
    @ColumnInfo(name = "first_name") var userName: String?,

    var photoUrl: String?,

    @ColumnInfo(name = "login_type") var loginType: String?
)
