package julienbirabent.apollomusic.ui.objective.criteria

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentObjectiveCriteriaSelectionBinding
import julienbirabent.apollomusic.ui.adapters.criteria.CriteriaSelectionAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.objective.ObjectiveCreateNavigator
import julienbirabent.apollomusic.ui.objective.ObjectiveCreateViewModel

class ObjectiveCriteriaSelectionFragment :
    BaseFragment<FragmentObjectiveCriteriaSelectionBinding, ObjectiveCreateViewModel>(),
    ObjectiveCreateNavigator {

    private lateinit var criteriaAdapter: CriteriaSelectionAdapter

    override fun goToCriteriaSelection() {
        //Not needed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.criteriaList.observe(viewLifecycleOwner, Observer {
            criteriaAdapter.updateList(it)
        })
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