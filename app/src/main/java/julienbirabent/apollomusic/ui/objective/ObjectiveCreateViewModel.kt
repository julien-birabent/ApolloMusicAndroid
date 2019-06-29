package julienbirabent.apollomusic.ui.objective

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import julienbirabent.apollomusic.data.local.Difficulty
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.repository.CriteriaRepository
import julienbirabent.apollomusic.ui.adapters.CheckedWrapper
import julienbirabent.apollomusic.ui.adapters.ItemSelectionCallback
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectiveCreateViewModel @Inject constructor(private val criteriaRepo: CriteriaRepository) :
    BaseViewModel<ObjectiveCreateNavigator>() {

    private val currentUser: LiveData<UserEntity> = criteriaRepo.currentUserLiveData

    val customObjectiveString: MutableLiveData<String> = MutableLiveData()
    val canGoToCriteriaSelection: MediatorLiveData<Boolean> = MediatorLiveData()

    //region Exercises
    val exerciseSelected: MutableLiveData<ExerciseEntity> = MutableLiveData()
    var exerciseList: MutableLiveData<List<ExerciseEntity>> = MutableLiveData()
    val exerciseItemCallback: ItemSelectionCallback<ExerciseEntity> = object :
        ItemSelectionCallback<ExerciseEntity> {
        override fun onItemSelected(item: ExerciseEntity) {
            if (item == exerciseSelected.value) {
                exerciseSelected.value = null
            } else {
                exerciseSelected.value = item
            }
        }
    }
    //endregion

    //region Criterias variables
    val criteriaSelected: MutableLiveData<CriteriaEntity> = MutableLiveData()
    private var _criteriaList: LiveData<List<CriteriaEntity>> = Transformations.switchMap(currentUser) {
        currentUser.value?.id?.let { it1 -> criteriaRepo.getCriteriaList(it1) }
    }
    private var criteriaList: LiveData<MutableList<CheckedWrapper<CriteriaEntity>>> = Transformations.map(
        _criteriaList
    ) {
        val list = mutableListOf<CheckedWrapper<CriteriaEntity>>()
        if (it != null) {
            for (criteria in it) {
                val newCriteriaWrapper = CheckedWrapper(criteria)
                if (criteria == criteriaSelected.value) {
                    newCriteriaWrapper.checked.set(true)
                } else {
                    newCriteriaWrapper.checked.set(false)
                }
                list.add(newCriteriaWrapper)
            }
        }
        list
    }

    val criteriaCallback: ItemSelectionCallback<CriteriaEntity> = object :
        ItemSelectionCallback<CriteriaEntity> {

        override fun onItemSelected(item: CriteriaEntity) {
            if (item == criteriaSelected.value) {
                criteriaSelected.value = null
            } else {
                criteriaSelected.value = item
            }
            updateCriteriaListStates()
        }
    }
    //endregion

    init {

        exerciseList.postValue(
            listOf(
                ExerciseEntity(-1, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(0, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(1, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(2, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(3, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(4, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(5, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(16, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(-16, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(-18, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE),
                ExerciseEntity(-15, "1", "Fake exercise", "Fake Description", null, 80, Difficulty.GODLIKE)
            )
        )

        exerciseSelected.postValue(null)
        criteriaSelected.postValue(null)
        canGoToCriteriaSelection.postValue(false)

        canGoToCriteriaSelection.addSource(customObjectiveString) {
            canGoToCriteriaSelection.postValue(it.isNotEmpty().or(exerciseSelected.value != null))
        }
    }

    fun getCriteriaList(): LiveData<MutableList<CheckedWrapper<CriteriaEntity>>> {
        return this.criteriaList
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
        criteriaRepo.saveCriteria(criteriaString)
    }

    /*******************************************************************************/

    fun goToExerciseSelection() {
        navigator.goToExerciseSelection()
    }

    fun goToObjectiveTypeSelection() {
        navigator.goToObjectiveTypeSelection()
    }

    fun createObjective() {
        //Create objective

        // if creation successful go to create practice
        navigator.goToPracticeCreation()
    }
}

