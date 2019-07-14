package julienbirabent.apollomusic.ui.practice.list

import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import julienbirabent.apollomusic.ui.adapters.practice.PracticeItemCallback
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeListViewModel @Inject constructor() :
    BaseViewModel<PracticeListNavigator>() {

    val practiceList: MutableLiveData<List<Any>> = MutableLiveData()

    val practiceItemCallback: PracticeItemCallback = object : PracticeItemCallback {
        override fun openPractice(item: PracticeEntity) {
            navigator?.goToPracticePage()
        }
    }

    init {
        practiceList.postValue(emptyList())
    }
}