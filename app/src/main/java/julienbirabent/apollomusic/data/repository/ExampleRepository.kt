package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import julienbirabent.apollomusic.data.api.network.NetworkBoundResource
import julienbirabent.apollomusic.data.api.network.Resource
import julienbirabent.apollomusic.data.api.services.ExampleAPI
import julienbirabent.apollomusic.data.local.dao.ExampleDao
import julienbirabent.apollomusic.data.local.entities.ExampleEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExampleRepository @Inject constructor(
    private val exampleDao: ExampleDao,
    private val exampleService: ExampleAPI
): BaseRepository() {

    @SuppressLint("CheckResult")
    fun getAnExample() {
        exampleService.getSingleExample()
            .doOnNext {
                it.body()?.let { example ->
                    exampleDao.insert(example)

                    Log.e("LOL", "ExampleEntity insertion completed")
                }
            }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe()

    }

    fun getExampleById(id: String): LiveData<Resource<ExampleEntity>> {
        return object : NetworkBoundResource<ExampleEntity, ExampleEntity>(appExecutors) {
            override fun saveCallResult(item: ExampleEntity) {
                exampleDao.insert(item)
            }

            override fun shouldFetch(data: ExampleEntity?) = data == null

            override fun loadFromDb() = exampleDao.loadSingle(id)

            override fun createCall() = exampleService.testIfServerRunning()
        }.asLiveData()
    }

    fun getAllExamples(): LiveData<List<ExampleEntity>> = exampleDao.loadAllExamples()
}