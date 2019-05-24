package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import julienbirabent.apollomusic.data.local.entities.User

@Dao
interface UserDao {

    @Delete
    fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE id = :id ")
    fun getUserWithId(id: String): LiveData<User>
}
