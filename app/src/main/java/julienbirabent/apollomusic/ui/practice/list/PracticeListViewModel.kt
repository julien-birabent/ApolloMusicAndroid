package julienbirabent.apollomusic.ui.practice.list

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import julienbirabent.apollomusic.data.local.model.SimpleTextItem
import julienbirabent.apollomusic.data.repository.PracticeRepository
import julienbirabent.apollomusic.ui.adapters.practice.PracticeItemCallback
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeListViewModel @Inject constructor(practiceRepo: PracticeRepository) :
    BaseViewModel<PracticeListNavigator>() {

    private var _practiceList: LiveData<List<PracticeEntity>> = practiceRepo.getPracticeList()
    val practiceList: LiveData<List<Any>>

    val practiceItemCallback: PracticeItemCallback = object : PracticeItemCallback {
        override fun openTodayPractice(item: PracticeEntity) {
            navigator?.goToPracticePage()
        }
    }

    init {
        practiceList = Transformations.map(_practiceList) {
            if (it.isEmpty()) {
                return@map emptyList<Any>()
            } else {
                return@map preparePracticeListForUi(it)
            }
        }
    }

    private fun preparePracticeListForUi(inputList: List<PracticeEntity>): List<Any> {
        val outputList: MutableList<Any> = mutableListOf()
        val copyInputList = inputList.toMutableList()
        copyInputList.sortBy { it.date }

        outputList.add(SimpleTextItem(R.string.header_practice_today))

        copyInputList.filter { DateUtils.isToday(it.date.time) }.apply {
            forEach { practice ->
                outputList.add(practice)
            }
        }
        outputList.add(SimpleTextItem(R.string.header_practice_to_come))

        copyInputList.filter { !DateUtils.isToday(it.date.time) }.apply {
            forEach { practice ->
                outputList.add(practice)
            }
        }
        return outputList
    }

}