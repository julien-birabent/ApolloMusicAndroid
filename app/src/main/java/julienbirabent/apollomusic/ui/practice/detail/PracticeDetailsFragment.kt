package julienbirabent.apollomusic.ui.practice.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeDetailsBinding
import julienbirabent.apollomusic.ui.adapters.objective.ObjectiveDetailAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment

class PracticeDetailsFragment : BaseFragment<FragmentPracticeDetailsBinding, PracticeDetailsViewModel>(),
    PracticeDetailsNavigator {

    private val args: PracticeDetailsFragmentArgs by navArgs()
    private lateinit var objAdapter: ObjectiveDetailAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            practiceId.postValue(args.practiceId)
            practiceBundle.observe(viewLifecycleOwner, Observer {
                objAdapter.currentPracticeDate = it.practice.date
                objAdapter.updateList(it.ObjBundleList)
            })
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        objAdapter = ObjectiveDetailAdapter(viewModel.objectiveDetailsItemCallback)
        binding.setVariable(BR.objAdapter, objAdapter)
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

    override fun startPractice(objId: Int) {
        findNavController().navigate(PracticeDetailsFragmentDirections.startExercise(objId))
    }
}
