package julienbirabent.apollomusic.ui.practice.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import julienbirabent.apollomusic.data.local.model.ObjectiveBundle
import julienbirabent.apollomusic.data.repository.ObjectiveRepository
import julienbirabent.apollomusic.data.repository.PracticeRepository
import julienbirabent.apollomusic.ui.adapters.ActionItem
import julienbirabent.apollomusic.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeCreateViewModel @Inject constructor(
    private val objRepo: ObjectiveRepository,
    private val practiceRepository: PracticeRepository
) :
    BaseViewModel<PracticeCreateNavigator>() {

    val practiceNotes = MutableLiveData<String>()
    var practiceCreatedValidated: MutableLiveData<Boolean> = MutableLiveData()

    private var practiceDates = MutableLiveData<MutableList<Date>>()
    val datesEmpty: LiveData<Boolean> = Transformations.map(practiceDates) { it.size > 0 }
    val objList: MutableLiveData<List<ObjectiveBundle>> = objRepo.getPracticeCreationObjListLive()
    val objEmpty: LiveData<Boolean> = Transformations.map(objList) { it.isNotEmpty() }

    val dateActionItemCallback: ActionItem<Date> = object :
        ActionItem<Date> {
        override fun modifyItem(item: Date) {
            navigator?.goToSimpleSelectionCalendar(item)
        }

        override fun deleteItem(item: Date) {
            deleteDate(item)
        }
    }

    val objActionCallback: ActionItem<ObjectiveBundle> = object : ActionItem<ObjectiveBundle> {
        override fun modifyItem(item: ObjectiveBundle) {

        }

        override fun deleteItem(item: ObjectiveBundle) {
            objRepo.deletePendingObj(item)
        }
    }

    init {
        practiceDates.value = mutableListOf()
    }

    private fun isPracticeCreationPossible(): Boolean {
        return if (objEmpty.value == null || datesEmpty.value == null) {
            false
        } else {
            objEmpty.value!! && datesEmpty.value!!
        }
    }

    fun manualClear() {
        practiceDates.value = mutableListOf()
        practiceNotes.value = ""
        practiceCreatedValidated.value = false
        objRepo.resetObjCache()
    }

    fun openMultiSelectionCalendar() {
        navigator?.goToMultiSelectionCalendar()
    }

    fun goToCreateObjectivePage() {
        navigator?.goToCreateObjectivePage()
    }

    fun addDatesToPractice(dates: List<Date>) {
        practiceDates.value?.addAll(dates)
        practiceDates.value = practiceDates.value
    }

    fun replaceDate(previousDate: Date, newDate: Date) {
        deleteDate(previousDate)
        addDate(newDate)
    }

    /**
     * For testing purpose
     */
    fun getPracticesDates(): MutableLiveData<MutableList<Date>> {
        return practiceDates
    }

    private fun addDate(date: Date) {
        practiceDates.value?.add(date)
        practiceDates.value = practiceDates.value
    }

    private fun deleteDate(date: Date) {
        practiceDates.value?.remove(date)
        practiceDates.value = practiceDates.value
    }

    fun createPractice() {
        if (isPracticeCreationPossible()) {
            practiceDates.value?.let {
                val createPracticeLiveData = practiceRepository.createPractices(
                    it,
                    objRepo.getPracticeCreationObjectiveList(),
                    practiceNotes.value
                )
                practiceCreatedValidated = Transformations.map(createPracticeLiveData) {
                    !it.contains(false)
                } as MutableLiveData<Boolean>
                practiceCreatedValidated.observeForever { practiceSuccessfulyCreated ->
                    if (practiceSuccessfulyCreated) {
                        practiceCreatedValidated.removeObserver { }
                        manualClear()
                        navigator.returnToPracticeList()
                    } 
                }
            }
        } else {
            navigator.practiceContentMissingError()
        }
    }
}