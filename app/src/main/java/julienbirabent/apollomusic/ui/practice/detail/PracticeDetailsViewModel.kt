package julienbirabent.apollomusic.ui.practice.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import julienbirabent.apollomusic.data.local.model.ObjectiveBundle
import julienbirabent.apollomusic.data.repository.ObjectiveRepository
import julienbirabent.apollomusic.data.repository.PracticeRepository
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeDetailsViewModel @Inject constructor(
    private val practiceRepo: PracticeRepository,
    private val objectiveRepository: ObjectiveRepository,
    private val scheduler: AppSchedulerProvider,
    private val context: Context
) :
    BaseViewModel<PracticeDetailsNavigator>() {

    var practiceId: MutableLiveData<Int> = MutableLiveData()

    val practice: LiveData<PracticeEntity> = Transformations.switchMap(practiceId) { practiceId ->
        practiceRepo.fetchPractice(practiceId)
            .subscribeOn(scheduler.ui())
            .observeOn(scheduler.io())
            .doOnSubscribe { isLoading.set(true) }
            .doOnComplete { isLoading.set(false) }
            .subscribe({
                Log.d(PracticeDetailsViewModel::class.simpleName, "Fetch practice with id $practiceId is a success")
            }, {
                Log.d(PracticeDetailsViewModel::class.simpleName, "Fetch practice with id $practiceId has failed", it)
            })

        practiceRepo.getPractice(practiceId)
    }

    val objBundleList: LiveData<List<ObjectiveBundle>> = Transformations.switchMap(practice) {
        it.id?.let { practiceId -> objectiveRepository.getObjectiveBundleList(practiceId, compositeDisposable) }
    }

    val practiceNotes: LiveData<String> = Transformations.map(practice) {
        it.userNotes ?: context.getString(R.string.no_notes)
    }


}