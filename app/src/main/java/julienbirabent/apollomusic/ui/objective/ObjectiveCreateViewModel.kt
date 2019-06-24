package julienbirabent.apollomusic.ui.objective

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.repository.CriteriaRepository
import julienbirabent.apollomusic.ui.adapters.CheckedWrapper
import julienbirabent.apollomusic.ui.adapters.criteria.CriteriaSelectionCallback
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectiveCreateViewModel @Inject constructor(private val criteriaRepo: CriteriaRepository) :
    BaseViewModel<ObjectiveCreateNavigator>() {

    //region Criterias variables
    val criteriaSelected: MutableLiveData<CriteriaEntity> = MutableLiveData()
    private var _criteriaList: LiveData<List<CriteriaEntity>> = criteriaRepo.getCriteriasList()
    lateinit var criteriaList: MutableLiveData<MutableList<CheckedWrapper<CriteriaEntity>>>

    val criteriaCallback: CriteriaSelectionCallback = object :
        CriteriaSelectionCallback {

        override fun onCriteriaSelected(criteria: CriteriaEntity) {
            if (criteria == criteriaSelected.value) {
                criteriaSelected.value = null
            } else {
                criteriaSelected.value = criteria
            }
            updateCriteriaListStates()
        }
    }
    //endregion

    init {
        /*criteriaList.postValue(
            listOf(
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria2", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria2", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria3", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria1", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria2", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria3", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria1", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria2", false)),
                CheckedWrapper(CriteriaEntity(-1, 0, "FakeCriteria3", false))
            ).toMutableList()
        )*/
        criteriaSelected.postValue(null)
    }

    private fun updateCriteriaListStates() {
        criteriaList.value?.let {
            for (criteria in it) {
                criteria.checked.set(criteria.item == criteriaSelected.value)
            }
        }
    }

    fun goToCriteriaSelection() {
        navigator.goToCriteriaSelection()
    }

    fun isCriteriaInputValid(criteriaString: String): Boolean {
        return criteriaString.isNotEmpty()
    }

    fun createCriteria(criteriaString: String) {
        criteriaRepo.persistCriteria(criteriaString)
    }
}

