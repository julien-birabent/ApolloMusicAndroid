package julienbirabent.apollomusic.ui.adapters.objective

import julienbirabent.apollomusic.data.local.model.ObjectiveBundle

interface ObjectiveDetailsItemCallback {

    fun startExercise(objective: ObjectiveBundle)
}