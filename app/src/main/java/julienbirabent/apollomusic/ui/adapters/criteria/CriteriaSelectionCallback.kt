package julienbirabent.apollomusic.ui.adapters.criteria

import julienbirabent.apollomusic.data.local.entities.CriteriaEntity

interface CriteriaSelectionCallback {

    fun onCriteriaSelected(criteria: CriteriaEntity, isSelected: Boolean)

}