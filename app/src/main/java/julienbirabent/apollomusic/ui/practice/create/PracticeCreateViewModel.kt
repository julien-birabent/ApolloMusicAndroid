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

    private var practiceDates = MutableLiveData<MutableList<Date>>()
    val datesEmpty: LiveData<Boolean> = Transformations.map(practiceDates) { it.size > 0 }

    val dateActionItemCallback: ActionItem<Date> = object :
        ActionItem<Date> {
        override fun modifyItem(item: Date) {
            modifyDate(item)
        }

        override fun deleteItem(item: Date) {
            deleteDate(item)
        }
    }

    /**
     * For testing purpose
     */
    fun getPracticesDates(): MutableLiveData<MutableList<Date>> {
        practiceDates.value = mutableListOf(Date(), Date(), Date(), Date(), Date(), Date(), Date(), Date(), Date())
        return practiceDates
    }

    private fun modifyDate(date: Date) {

    }

    private fun deleteDate(date: Date) {
        practiceDates.value?.remove(date)
        practiceDates.postValue(practiceDates.value)
    }

}