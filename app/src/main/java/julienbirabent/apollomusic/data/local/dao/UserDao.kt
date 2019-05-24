package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import julienbirabent.apollomusic.data.local.entities.UserEntity

@Dao
interface UserDao {

    @Delete
    fun delete(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :id ")
    fun getUserWithId(id: String): LiveData<UserEntity>
}
