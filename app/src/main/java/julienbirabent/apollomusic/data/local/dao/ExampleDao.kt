package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import julienbirabent.apollomusic.data.local.entities.ExampleEntity

@Dao
interface ExampleDao {

    @Delete
    fun delete(example: ExampleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(example: ExampleEntity)

    @Query("SELECT * FROM examples")
    fun loadAllExamples(): LiveData<List<ExampleEntity>>

    @Query("SELECT * FROM examples WHERE id = :id ")
    fun loadSingle(id: String): LiveData<ExampleEntity>

    @Query("SELECT * FROM examples ORDER BY id DESC LIMIT 1")
    fun loadMostRecentExample(): LiveData<ExampleEntity>

}
