package julienbirabent.apollomusic.ui.objective

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentObjectiveCreateBinding
import julienbirabent.apollomusic.ui.base.BaseFragment

class ObjectiveCreateFragment : BaseFragment<FragmentObjectiveCreateBinding, ObjectiveCreateViewModel>(),
    ObjectiveCreateNavigator {


    override fun goToCriteriaSelection() {
        findNavController().navigate(R.id.action_objectiveCreateFragment_to_objectiveCriteriaSelectionFragment)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_objective_create
    }

    override fun getViewModel(): ObjectiveCreateViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(ObjectiveCreateViewModel::class.java)
    }
}