package julienbirabent.apollomusic.ui.objective.criteria

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.Utils.StateData
import julienbirabent.apollomusic.databinding.FragmentObjectiveCriteriaSelectionBinding
import julienbirabent.apollomusic.extensions.showInputDialog
import julienbirabent.apollomusic.ui.adapters.criteria.CriteriaSelectionAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.objective.ObjectiveCreateNavigator
import julienbirabent.apollomusic.ui.objective.ObjectiveCreateViewModel

class ObjectiveCriteriaSelectionFragment :
    BaseFragment<FragmentObjectiveCriteriaSelectionBinding, ObjectiveCreateViewModel>(),
    ObjectiveCreateNavigator {

    override fun goToObjectiveTypeSelection() {
        //Not needed
    }

    private lateinit var criteriaAdapter: CriteriaSelectionAdapter

    override fun goToPracticeCreation() {
        findNavController().navigate(R.id.action_objectiveCriteriaSelectionFragment_to_practiceCreateFragment)
    }

    override fun goToExerciseSelection() {
        //Not needed
    }

    override fun goToCriteriaSelection() {
        //Not needed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCriteriaList().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                StateData.DataStatus.SUCCESS ->
                    criteriaAdapter.updateList(it.data!!)
            }
        })

        viewDataBinding.addCriteriaButton.setOnClickListener {
            activity?.showInputDialog(
                getString(R.string.dialog_add_criteria_title),
                getString(R.string.dialog_add_criteria_message),
                this::onConfirmAddCriteria
            )
        }

        viewDataBinding.doneButton.setOnClickListener {
            viewModel.createObjective()
        }
    }

    private fun onConfirmAddCriteria(criteriaString: String): Boolean {
        return if (viewModel.isCriteriaInputValid(criteriaString)) {
            viewModel.createCriteria(criteriaString)
            true
        } else {
            false
        }
    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        criteriaAdapter =
            CriteriaSelectionAdapter(viewModel.criteriaCallback)
        binding.setVariable(BR.adapter, criteriaAdapter)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_objective_criteria_selection
    }

    override fun getViewModel(): ObjectiveCreateViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(ObjectiveCreateViewModel::class.java)
    }
}