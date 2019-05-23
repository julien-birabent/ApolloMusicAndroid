package julienbirabent.apollomusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import julienbirabent.apollomusic.data.local.entities.Example

@Dao
interface ExampleDao {

    @Delete
    fun delete(example: Example)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(example: Example)

    @Query("SELECT * FROM examples")
    fun loadAllExamples(): LiveData<List<Example>>

    @Query("SELECT * FROM examples WHERE id = :id ")
    fun loadSingle(id: String): LiveData<Example>

    @Query("SELECT * FROM examples ORDER BY id DESC LIMIT 1")
    fun loadMostRecentExample(): LiveData<Example>

}
