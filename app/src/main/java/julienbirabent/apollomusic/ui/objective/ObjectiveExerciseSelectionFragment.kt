package julienbirabent.apollomusic.ui.objective

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.Utils.StateData
import julienbirabent.apollomusic.databinding.FragmentObjectiveExerciseSelectionBinding
import julienbirabent.apollomusic.ui.adapters.exercise.ExerciseAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment


class ObjectiveExerciseSelectionFragment :
    BaseFragment<FragmentObjectiveExerciseSelectionBinding, ObjectiveCreateViewModel>(),
    ObjectiveCreateNavigator {

    override fun goToObjectiveTypeSelection() {
        findNavController().navigate(R.id.action_objectiveExerciseSelectionFragment_to_objectiveCreateFragment)
    }

    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun goToPracticeCreation() {
        //Not needed
    }

    override fun goToExerciseSelection() {
        //Not needed
    }

    override fun goToCriteriaSelection() {
        //Not needed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exerciseListState.observe(viewLifecycleOwner, Observer {
            Log.d("State live data test", "Status : " + it.status.toString())
            when (it.status) {
                StateData.DataStatus.SUCCESS -> {
                    exerciseAdapter.updateList(it.data!!)
                }
                StateData.DataStatus.ERROR -> Toast.makeText(
                    context,
                    R.string.network_error_could_not_fetch_last_exercises,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        exerciseAdapter = ExerciseAdapter(viewModel.exerciseItemCallback)
        binding.setVariable(BR.adapter, exerciseAdapter)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_objective_exercise_selection
    }

    override fun getViewModel(): ObjectiveCreateViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(ObjectiveCreateViewModel::class.java)
    }
}