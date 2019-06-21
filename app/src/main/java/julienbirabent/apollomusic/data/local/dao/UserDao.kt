package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import julienbirabent.apollomusic.data.local.entities.UserEntity


@Dao
abstract class UserDao {

    @Query("SELECT * FROM users WHERE id = :id ")
    abstract fun getUserById(id: String): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entity: UserEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun update(entity: UserEntity)

    @Delete
    abstract fun delete(user: UserEntity)

    fun upsert(entity: UserEntity) : String? {
        val id = insert(entity)
        // insert return -1 if the user is already in the database
        if (id == (-1).toLong()) {
            update(entity)
        }
        return entity.id
    }
}
