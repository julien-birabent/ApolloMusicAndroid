package julienbirabent.apollomusic.ui.adapters.objective

import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity

interface ObjectiveDetailsItemCallback {

    fun begin(objective: ObjectiveEntity)
}