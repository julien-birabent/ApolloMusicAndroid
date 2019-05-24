package julienbirabent.apollomusic.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    @ColumnInfo val email: String?,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    val photoUrl : String?,
    @ColumnInfo(name = "connection_method") val connectionMethod : String?
)
