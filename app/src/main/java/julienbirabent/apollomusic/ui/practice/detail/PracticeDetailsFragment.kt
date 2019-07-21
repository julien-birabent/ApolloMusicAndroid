package julienbirabent.apollomusic.ui.practice.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeDetailsBinding
import julienbirabent.apollomusic.ui.base.BaseFragment

class PracticeDetailsFragment : BaseFragment<FragmentPracticeDetailsBinding, PracticeDetailsViewModel>(),
    PracticeDetailsNavigator {

    private val args: PracticeDetailsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.practiceId = args.practiceId
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
        return R.layout.fragment_practice_details
    }

    override fun getViewModel(): PracticeDetailsViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(PracticeDetailsViewModel::class.java)
    }

    override fun startPractice() {

    }
}
