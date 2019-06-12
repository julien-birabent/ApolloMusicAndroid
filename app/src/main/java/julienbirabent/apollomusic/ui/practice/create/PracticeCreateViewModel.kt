package julienbirabent.apollomusic.ui.practice.create

import androidx.lifecycle.LiveData
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton
import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.ui.adapters.ActionItem
import java.util.*


@Singleton
class PracticeCreateViewModel @Inject constructor() :
    BaseViewModel<PracticeCreateNavigator>() {

    private var practiceDates = MutableLiveData<MutableList<Date>>()

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
    fun getPracticesDates() : MutableLiveData<MutableList<Date>> {
        practiceDates.value = mutableListOf(Date(), Date(), Date(),Date(), Date(), Date(),Date(), Date(), Date())
        return practiceDates
    }

    private fun modifyDate(date : Date){

    }

    private fun deleteDate(date : Date){
        practiceDates.value?.remove(date)
        practiceDates.postValue(practiceDates.value)
    }

}