package julienbirabent.apollomusic.ui.practice.list

import julienbirabent.apollomusic.ui.base.UINavigator

interface PracticeListNavigator : UINavigator {

    fun goToPracticePage()

    fun showPracticeFetchCompleted(successful: Boolean)
}
