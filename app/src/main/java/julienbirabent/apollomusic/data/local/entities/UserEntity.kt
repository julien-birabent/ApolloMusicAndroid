package julienbirabent.apollomusic.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose


@Entity(tableName = "users")
data class UserEntity(

    @Expose
    @PrimaryKey var id: String,

    @Expose
    @ColumnInfo var email: String?,

    @Expose
    @ColumnInfo(name = "first_name") var firstName: String?,

    @Expose
    @ColumnInfo(name = "last_name") var lastName: String?,

    @Expose
    var photoUrl: String?,

    @ColumnInfo(name = "login_type") var loginType: String?
)
