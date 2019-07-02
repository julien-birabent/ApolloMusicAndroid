package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import julienbirabent.apollomusic.data.api.services.ExerciseAPI
import julienbirabent.apollomusic.data.local.Difficulty
import julienbirabent.apollomusic.data.local.dao.ExerciseDao
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExercisesRepository @Inject constructor(
    private val exercisesApi: ExerciseAPI,
    private val exerciseDao: ExerciseDao
) : BaseRepository() {

    fun enableUpdatesOnConnectionAvailable(composite: CompositeDisposable) {
        composite.add(connectionAvailableEmitter()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.io())
            .flatMap { getExercisesFromServer() }
            .subscribe({
                Log.d(
                    ExercisesRepository::class.simpleName,
                    "Fetching exercises on connection gained ${it.size}"
                )
            }, {
                Log.e(ExercisesRepository::class.simpleName, "An error happened : " + it.message)
            })
        )
    }

    private fun getExercises(): Observable<List<ExerciseEntity>> {
        return Observable.concatArray(
            getExercisesFromDb(),
            getExercisesFromServer()
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
        Observable.fromCallable { exerciseDao.insert(*exercises.toTypedArray()) }
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.io())
            .subscribe {
                Log.d(ExercisesRepository::class.simpleName, "Inserted ${exercises.size} exercises from API in DB...")
            }
    }

    fun getAllexercises(): LiveData<List<ExerciseEntity>> {
        return exerciseDao.getAllExercisesLive()
    }
}
