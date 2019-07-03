package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import julienbirabent.apollomusic.Utils.StateLiveData
import julienbirabent.apollomusic.data.api.services.ExerciseAPI
import julienbirabent.apollomusic.data.local.dao.ExerciseDao
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesRepository @Inject constructor(
    private val exercisesApi: ExerciseAPI,
    private val exerciseDao: ExerciseDao
) : BaseRepository() {

    private val exercisesFromServer: Observable<List<ExerciseEntity>> = getExercisesFromServer()
    private val exercisesFromDb: Observable<List<ExerciseEntity>> = getExercisesFromDb()

    fun exercisesFromServerUpdates(): Observable<List<ExerciseEntity>> {
        return connectionAvailableEmitter()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .flatMap { getExercisesFromServer() }
            .doOnSubscribe {
                Log.d(ExercisesRepository::class.simpleName, "Subscribing to network changes")
            }
            .doOnNext {
                Log.d(ExercisesRepository::class.simpleName, "Fetching exercises on connection gained ${it.size}")
            }
            .doOnError {
                Log.e(ExercisesRepository::class.simpleName, "An error happened : " + it.message)
            }
    }

    fun getExercises(): StateLiveData<List<ExerciseEntity>> {
        return StateLiveData(
            scheduler,
            exercisesFromDb, exercisesFromServerUpdates()
        )
    }

    private fun getExercisesFromDb(): Observable<List<ExerciseEntity>> {
        return exerciseDao.getAllExercises()
            .filter { it.isNotEmpty() }
            .doOnNext {
                Log.d(ExercisesRepository::class.simpleName, "Dispatching ${it.size} exercise from DB...")
            }
    }

    private fun getExercisesFromServer(): Observable<List<ExerciseEntity>> {
        return exercisesApi.getAllExercises()
            .map { it.body()!! }
            .doOnSuccess {
                Log.d(ExercisesRepository::class.simpleName, "Dispatching ${it.size} exercise from API...")
                storeExercisesInDb(it)
            }.toObservable()
    }

    @SuppressLint("CheckResult")
    private fun storeExercisesInDb(exercises: List<ExerciseEntity>) {
        appExecutors.diskIO().execute {
            exerciseDao.insert(*exercises.toTypedArray())
        }
    }
}
