package julienbirabent.apollomusic.ui.objective

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentObjectiveCreateBinding
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_home.*

class ObjectiveCreateFragment : BaseFragment<FragmentObjectiveCreateBinding, ObjectiveCreateViewModel>(),
    ObjectiveCreateNavigator {

    override fun goToObjectiveTypeSelection() {
        toolbar.setTitle("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.manualClear()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is HomeActivity) {
            activity.hideBottomNavigation(true)
        }
    }


    override fun goToExerciseSelection() {
        findNavController().navigate(R.id.action_objectiveCreateFragment_to_objectiveExerciseSelectionFragment)
    }

    override fun goToPracticeCreation() {
    }

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