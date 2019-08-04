package julienbirabent.apollomusic.ui.play_exercise

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPlayExerciseBinding
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.base.HideToolbarCallback
import julienbirabent.apollomusic.ui.home.HomeActivity

class PlayExerciseFragment : BaseFragment<FragmentPlayExerciseBinding, PlayExerciseViewModel>(),
    PlayExerciseNavigator, HideToolbarCallback {

    private val args: PlayExerciseFragmentArgs by navArgs()

    override fun onHideToolbar(toolbarIsShown: Boolean) {
        super.onHideToolbar(toolbarIsShown)
        if (activity is HomeActivity) {
            (activity as HomeActivity).hideBottomNavigation(!toolbarIsShown)
        }
    }

    override fun getDefaultScreenOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun endSession() {
        activity?.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.objTrigger.observe(viewLifecycleOwner, Observer {
            viewDataBinding.tablatureView.tablature = it.ex?.tablature
            viewDataBinding.desiredPracticeTime.text =
                String.format(getString(R.string.desired_practice_time), it.obj.targetPracticeTime.toString())
            viewDataBinding.exerciseTitle.text = String.format(getString(R.string.title), it.ex?.title)
            viewDataBinding.exerciseDescription.text =
                String.format(getString(R.string.description), it.ex?.description)
        })
        viewModel.currentPracticeTime.observe(viewLifecycleOwner, Observer {
            viewDataBinding.actualPracticeTime.text = String.format(getString(R.string.current_practice_time), it)
        })
        viewModel.objectiveId.value = args.ObjectiveId
        viewDataBinding.doneButton.setOnClickListener { viewModel.doneAction(args.ObjectiveId) }
    }


    override fun onResume() {
        super.onResume()
    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_play_exercise
    }

    override fun getViewModel(): PlayExerciseViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(PlayExerciseViewModel::class.java)
    }

}
