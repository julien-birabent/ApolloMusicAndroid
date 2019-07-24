package julienbirabent.apollomusic.ui.play_exercise

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPlayExerciseBinding
import julienbirabent.apollomusic.ui.base.BaseFragment

class PlayExerciseFragment : BaseFragment<FragmentPlayExerciseBinding, PlayExerciseViewModel>(),
    PlayExerciseNavigator {

/*    private val args: PracticeDetailsFragmentArgs by navArgs()
    private lateinit var objAdapter: ObjectiveDetailAdapter*/

    override fun endSession() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
