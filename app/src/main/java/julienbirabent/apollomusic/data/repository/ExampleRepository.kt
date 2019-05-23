package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.functions.Consumer
import julienbirabent.apollomusic.data.api.network.NetworkBoundResource
import julienbirabent.apollomusic.data.api.network.Resource
import julienbirabent.apollomusic.data.api.services.ExampleService
import julienbirabent.apollomusic.data.local.dao.ExampleDao
import julienbirabent.apollomusic.data.local.entities.Example
import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExampleRepository @Inject constructor(
    private val exampleDao: ExampleDao,
    private val exampleService: ExampleService,
    private val appExecutors: AppExecutors,
    private val scheduler: SchedulerProvider
) {

    @SuppressLint("CheckResult")
    fun getAnExample() {

        exampleService.getSingleExample()
            .doOnNext {
                it.body()?.let { example ->
                    exampleDao.insert(example)

                    Log.e("LOL", "Example insertion completed")
                }
            }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe()

    }

    fun getExampleById(id: String): LiveData<Resource<Example>> {
        return object : NetworkBoundResource<Example, Example>(appExecutors) {
            override fun saveCallResult(item: Example) {
                exampleDao.insert(item)
            }

            override fun shouldFetch(data: Example?) = data == null

            override fun loadFromDb() = exampleDao.loadSingle(id)

            override fun createCall() = exampleService.testIfServerRunning()
        }.asLiveData()
    }

    fun getAllExamples(): LiveData<List<Example>> = exampleDao.loadAllExamples()
}