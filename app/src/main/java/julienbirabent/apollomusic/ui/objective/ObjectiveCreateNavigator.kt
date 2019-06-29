package julienbirabent.apollomusic.ui.objective

import julienbirabent.apollomusic.ui.base.UINavigator

interface ObjectiveCreateNavigator : UINavigator{

    fun goToCriteriaSelection()

    fun goToExerciseSelection()

    fun goToPracticeCreation()
}
