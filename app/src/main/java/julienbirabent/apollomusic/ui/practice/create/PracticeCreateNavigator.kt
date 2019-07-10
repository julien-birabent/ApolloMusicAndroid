package julienbirabent.apollomusic.ui.practice.create

import julienbirabent.apollomusic.ui.base.UINavigator
import java.util.*

interface PracticeCreateNavigator: UINavigator{

    fun goToMultiSelectionCalendar()

    fun goToSimpleSelectionCalendar(date : Date)

    fun goToCreateObjectivePage()

    fun returnToPracticeList()
}