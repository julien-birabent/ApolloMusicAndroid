package julienbirabent.apollomusic.ui.objective

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentObjectiveExerciseSelectionBinding
import julienbirabent.apollomusic.ui.base.BaseFragment


class ObjectiveExerciseSelectionFragment :
    BaseFragment<FragmentObjectiveExerciseSelectionBinding, ObjectiveCreateViewModel>(),
    ObjectiveCreateNavigator {

    override fun goToExerciseSelection() {
        //Not needed
    }

    override fun goToCriteriaSelection() {
        //Not needed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        /*criteriaAdapter =
            CriteriaSelectionAdapter(viewModel.criteriaCallback)
        binding.setVariable(BR.adapter, criteriaAdapter)*/
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