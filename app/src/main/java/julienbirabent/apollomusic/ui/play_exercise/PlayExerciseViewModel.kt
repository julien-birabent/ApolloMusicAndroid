package julienbirabent.apollomusic.ui.play_exercise

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import julienbirabent.apollomusic.data.local.model.ObjectiveBundle
import julienbirabent.apollomusic.data.repository.ObjectiveRepository
import julienbirabent.apollomusic.data.repository.PracticeRepository
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.ui.base.BaseViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PlayExerciseViewModel @Inject constructor(
    private val practiceRepo: PracticeRepository,
    private val objectiveRepository: ObjectiveRepository,
    private val scheduler: AppSchedulerProvider
) : BaseViewModel<PlayExerciseNavigator>() {

    val objectiveId: MutableLiveData<Int> = MutableLiveData()
    val currentPracticeTime: MutableLiveData<String> = MutableLiveData()

    private val _currentPracticeTime: Observable<String> = Observable.interval(1, TimeUnit.SECONDS)
        .observeOn(scheduler.io())
        .subscribeOn(scheduler.ui())
        .map { secondsToTimeFormatted(it) }


    val objTrigger: MediatorLiveData<ObjectiveBundle> = MediatorLiveData()

    init {
        objTrigger.addSource(objectiveId) { objId ->
            objectiveRepository.getObjectiveBundle(objId)
                .subscribeOn(scheduler.ui())
                .observeOn(scheduler.io())
                .subscribe({
                    objTrigger.postValue(it)
                    Log.d(PlayExerciseViewModel::class.simpleName, "Objective Bundle has been loaded from args : $it")
                }, {
                    Log.d(PlayExerciseViewModel::class.simpleName, it.message, it)
                })
        }
        compositeDisposable.add(_currentPracticeTime.subscribe {
            currentPracticeTime.postValue(it)
        })
    }

    private fun secondsToTimeFormatted(totalSeconds: Long): String {
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

}
