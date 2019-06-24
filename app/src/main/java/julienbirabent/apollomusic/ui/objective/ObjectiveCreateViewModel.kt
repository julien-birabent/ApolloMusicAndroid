package julienbirabent.apollomusic.ui.objective

import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.ui.adapters.CheckedWrapper
import julienbirabent.apollomusic.ui.adapters.criteria.CriteriaSelectionCallback
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectiveCreateViewModel @Inject constructor() :
    BaseViewModel<ObjectiveCreateNavigator>() {

    //region Criterias variables
    val criteriaSelected: MutableLiveData<CriteriaEntity> = MutableLiveData()
    val criteriaList: MutableLiveData<MutableList<CheckedWrapper<CriteriaEntity>>> = MutableLiveData()

    val criteriaCallback: CriteriaSelectionCallback = object :
        CriteriaSelectionCallback {

        override fun onCriteriaSelected(criteria: CriteriaEntity, isSelected: Boolean) {
            if (criteria == criteriaSelected.value || !isSelected) {
                criteriaSelected.value = null
            } else {
                criteriaSelected.value = criteria
            }
            updateCriteriaListStates()
        }
    }
    //endregion

    init {
        criteriaList.postValue(
            listOf(
                CheckedWrapper(CriteriaEntity(0, 0, "FakeCriteria1")),
                CheckedWrapper(CriteriaEntity(1, 0, "FakeCriteria2")),
                CheckedWrapper(CriteriaEntity(2, 0, "FakeCriteria3")),
                CheckedWrapper(CriteriaEntity(3, 0, "FakeCriteria1")),
                CheckedWrapper(CriteriaEntity(4, 0, "FakeCriteria2")),
                CheckedWrapper(CriteriaEntity(5, 0, "FakeCriteria3")),
                CheckedWrapper(CriteriaEntity(6, 0, "FakeCriteria1")),
                CheckedWrapper(CriteriaEntity(7, 0, "FakeCriteria2")),
                CheckedWrapper(CriteriaEntity(8, 0, "FakeCriteria3"))
            ).toMutableList()
        )
        criteriaSelected.postValue(null)
    }

    private fun updateCriteriaListStates() {
        criteriaList.value?.let {
            for (criteria in it) {
                if (criteriaSelected.value != null) {
                    criteria.checked.set(criteria.item == criteriaSelected.value)
                }
            }
        }
    }

    fun goToCriteriaSelection() {
        navigator.goToCriteriaSelection()
    }
}

