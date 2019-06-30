package julienbirabent.apollomusic.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import julienbirabent.apollomusic.ui.login.LoginType


@Entity(tableName = "users")
data class UserEntity(

    @Expose
    @SerializedName("id")
    @PrimaryKey var id: String,

    // for keeping the id given by facebook or google
    var externalId: String,

    @Embedded
    @Expose
    @SerializedName("token")
    var token : Token?,

    @Expose
    @ColumnInfo var email: String?,

    @ColumnInfo(name = "username") var userName: String?,

    var photoUrl: String?,

    @ColumnInfo(name = "login_type") var loginType: LoginType
)
