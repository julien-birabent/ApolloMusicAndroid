package julienbirabent.apollomusic.data.local

import androidx.room.*
import julienbirabent.apollomusic.data.local.entities.User

@Dao
interface UserDao {

    @Delete
    fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

/*    @Query("SELECT * FROM users WHERE email IN (:userIds)")
    fun loadAllByIds(userIds: List<Int>): List<User>*/
}
