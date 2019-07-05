package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
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

    private val exercisesFromDb: Observable<List<ExerciseEntity>> = getExercisesFromDb()

    private fun exercisesFromServerUpdates(): Observable<List<ExerciseEntity>> {
        return connectionAvailableEmitter()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .flatMapSingle { getExercisesFromServer() }
            .doOnSubscribe {
                Log.d(ExercisesRepository::class.simpleName, "Subscribing to network changes")
            }
            .doOnNext {
                Log.d(ExercisesRepository::class.simpleName, "Fetching exercises on connection gained ${it.size}")
            }
            .doOnError {
                Log.e(ExercisesRepository::class.simpleName, "An error happened : " + it.message)
            }.doOnDispose {
                Log.d(ExercisesRepository::class.simpleName, "Unsubscribing to network changes")
            }
    }

    fun getExercises(): StateLiveData<List<ExerciseEntity>> {
        return StateLiveData(
            scheduler,
            Observable.concatArrayEager(exercisesFromDb, exercisesFromServerUpdates())
        )
    }

    private fun getExercisesFromDb(): Observable<List<ExerciseEntity>> {
        return exerciseDao.getAllExercises()
            .doOnNext {
                Log.d(ExercisesRepository::class.simpleName, "Dispatching ${it.size} exercise from DB...")
            }
    }

    private fun getExercisesFromServer(): Single<List<ExerciseEntity>> {
        return exercisesApi.getAllExercises()
            .map { it.body()!! }
            .doOnSuccess {
                Log.d(ExercisesRepository::class.simpleName, "Dispatching ${it.size} exercise from API...")
                storeExercisesInDb(it)
            }
    }

    @SuppressLint("CheckResult")
    private fun storeExercisesInDb(exercises: List<ExerciseEntity>) {
        appExecutors.diskIO().execute {
            exerciseDao.insert(*exercises.toTypedArray())
        }
    }
}
