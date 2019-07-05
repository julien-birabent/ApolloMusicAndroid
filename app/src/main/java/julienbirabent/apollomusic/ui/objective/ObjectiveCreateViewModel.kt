package julienbirabent.apollomusic.ui.objective

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import julienbirabent.apollomusic.Utils.StateData
import julienbirabent.apollomusic.Utils.StateLiveData
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.data.local.entities.UserEntity
import julienbirabent.apollomusic.data.repository.CriteriaRepository
import julienbirabent.apollomusic.data.repository.ExercisesRepository
import julienbirabent.apollomusic.data.repository.ObjectiveRepository
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.ui.adapters.CheckedWrapper
import julienbirabent.apollomusic.ui.adapters.ItemSelectionCallback
import julienbirabent.apollomusic.ui.base.BaseViewModel
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectiveCreateViewModel @Inject constructor(
    private val criteriaRepo: CriteriaRepository,
    private val exercisesRepository: ExercisesRepository,
    private val objectiveRepository: ObjectiveRepository,
    private val scheduler: AppSchedulerProvider
) :
    BaseViewModel<ObjectiveCreateNavigator>() {

    private val currentUser: LiveData<UserEntity> = criteriaRepo.currentUserLiveData

    val practiceTargetTime: MutableLiveData<String> = MutableLiveData()
    val customObjectiveString: MutableLiveData<String> = MutableLiveData()
    val canGoToCriteriaSelection: MediatorLiveData<Boolean> = MediatorLiveData()

    //region Exercises
    var exerciseTempo: MutableLiveData<String> = MutableLiveData()
    val exerciseSelected: MutableLiveData<ExerciseEntity> = MutableLiveData()
    var exerciseListState: StateLiveData<List<ExerciseEntity>> = exercisesRepository.getExercises()
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
    private var _criteriaList: LiveData<StateData<List<CriteriaEntity>>> = Transformations.switchMap(currentUser) {
        currentUser.value?.id?.let { userId ->
            criteriaRepo.getCriteriasLive(userId)
        }
    }
    private var criteriaList: LiveData<StateData<MutableList<CheckedWrapper<CriteriaEntity>>>> = Transformations.map(
        _criteriaList
    ) {
        val newValue = StateData<MutableList<CheckedWrapper<CriteriaEntity>>>()
        val list = mutableListOf<CheckedWrapper<CriteriaEntity>>()
        if (it != null && it.data != null && it.error == null && it.status == StateData.DataStatus.SUCCESS) {
            for (criteria in it.data!!) {
                val newCriteriaWrapper = CheckedWrapper(criteria)
                if (criteria == criteriaSelected.value) {
                    newCriteriaWrapper.checked.set(true)
                } else {
                    newCriteriaWrapper.checked.set(false)
                }
                list.add(newCriteriaWrapper)
            }
            return@map newValue.success(list)
        } else {
            return@map newValue.error(Exception())
        }
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
        init()
    }

    private fun init() {
        manualClear()
        canGoToCriteriaSelection.addSource(customObjectiveString) {
            if (it == null || exerciseSelected.value != null) {
                canGoToCriteriaSelection.postValue(true)
            } else {
                canGoToCriteriaSelection.postValue(it.isNotEmpty())
            }
        }
        canGoToCriteriaSelection.addSource(exerciseSelected) {
            if (it != null) {
                exerciseTempo.postValue(it.tempoBase.toString())

            }
            canGoToCriteriaSelection.postValue(it != null)
        }
    }

    fun manualClear() {
        exerciseSelected.postValue(null)
        criteriaSelected.postValue(null)
        canGoToCriteriaSelection.postValue(false)
        practiceTargetTime.postValue("0")
    }

    fun getCriteriaList(): LiveData<StateData<MutableList<CheckedWrapper<CriteriaEntity>>>> {
        return this.criteriaList
    }

    private fun updateCriteriaListStates() {
        criteriaList.value?.let {
            for (criteria in it.data!!) {
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
        compositeDisposable.add(
            criteriaRepo.saveCriteria(criteriaString)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .doOnSubscribe {
                    setIsLoading(true)
                }
                .doOnTerminate {
                    setIsLoading(false)
                }
                .doOnError {
                    setIsLoading(false)
                }
                .subscribe({
                    Log.d(ObjectiveCreateViewModel::class.qualifiedName, "Criteria $criteriaString successfuly added")
                }, {
                    if (it is UnknownHostException) {
                        // Probleme de connexion internet
                    }
                    setIsLoading(false)
                    Log.d(ObjectiveCreateViewModel::class.qualifiedName, "Criteria $criteriaString was not added")
                })
        )
    }

    /*******************************************************************************/

    fun goToExerciseSelection() {
        navigator.goToExerciseSelection()
    }

    fun goToObjectiveTypeSelection() {
        customObjectiveString.postValue(null)
        navigator.goToObjectiveTypeSelection()
    }

    fun createObjective() {
        //Create objective
        val criteriaSelected = criteriaSelected.value
        val exerciseSelected = exerciseSelected.value.apply { this?.tempoBase = exerciseTempo.value?.toInt() }
        val objTitle = exerciseSelected?.title ?: customObjectiveString.value

        objectiveRepository.createObjective(
            objTitle, practiceTargetTime.value?.toInt(), exerciseTempo.value?.toInt(),
            exerciseSelected?.copy(), criteriaSelected?.copy()
        )

        navigator.goToPracticeCreation()
        manualClear()
    }
}

