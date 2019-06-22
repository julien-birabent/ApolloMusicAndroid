package julienbirabent.apollomusic.ui.practice.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import julienbirabent.apollomusic.ui.adapters.ActionItem
import julienbirabent.apollomusic.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PracticeCreateViewModel @Inject constructor() :
    BaseViewModel<PracticeCreateNavigator>() {

    val practiceNotes = MutableLiveData<String>()

    private var practiceDates = MutableLiveData<MutableList<Date>>()
    val datesEmpty: LiveData<Boolean> = Transformations.map(practiceDates) { it.size > 0 }

    val dateActionItemCallback: ActionItem<Date> = object :
        ActionItem<Date> {
        override fun modifyItem(item: Date) {
            navigator?.goToSimpleSelectionCalendar(item)
        }

        override fun deleteItem(item: Date) {
            deleteDate(item)
        }
    }

    fun openMultiSelectionCalendar(){
        navigator?.goToMultiSelectionCalendar()
    }

    fun addDatesToPractice(dates: List<Date>){
        practiceDates.value?.addAll(dates)
        practiceDates.value = practiceDates.value
    }

    fun replaceDate(previousDate : Date, newDate: Date){
        deleteDate(previousDate)
        addDate(newDate)
    }

    /**
     * For testing purpose
     */
    fun getPracticesDates(): MutableLiveData<MutableList<Date>> {
        practiceDates.value = mutableListOf()
        return practiceDates
    }

    private fun addDate(date : Date){
        practiceDates.value?.add(date)
        practiceDates.value = practiceDates.value
    }

    private fun deleteDate(date: Date) {
        practiceDates.value?.remove(date)
        practiceDates.value = practiceDates.value
    }

}