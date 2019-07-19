package julienbirabent.apollomusic.ui.practice.list

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeListBinding
import julienbirabent.apollomusic.extensions.error
import julienbirabent.apollomusic.extensions.makeSnackBar
import julienbirabent.apollomusic.extensions.success
import julienbirabent.apollomusic.ui.adapters.practice.PracticeAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment

class PracticeListFragment : BaseFragment<FragmentPracticeListBinding, PracticeListViewModel>(),
    PracticeListNavigator {

    private lateinit var practiceAdapter: PracticeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {
            createPractice.setOnClickListener {
                findNavController().navigate(R.id.action_actionHome_to_practiceCreateFragment)
            }
        }

        viewModel.practiceList.observe(viewLifecycleOwner, Observer {
            practiceAdapter.updateList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshPracticeList()
    }

    override fun goToPracticePage(practiceId: Int) {
        findNavController().navigate(PracticeListFragmentDirections.goToPracticePage(practiceId))
    }

    override fun showPracticeFetchCompleted(successful: Boolean) {
        when (successful) {
            true -> activity?.makeSnackBar(getString(R.string.practice_successfully_fetched))?.success()
            false -> activity?.makeSnackBar(
                getString(R.string.practice_unsuccessfully_fetched),
                actionText = getString(R.string.retry), action = { viewModel.refreshPracticeList() }
            )?.error()
        }
    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        practiceAdapter = PracticeAdapter(viewModel.practiceItemCallback)
        binding.setVariable(BR.adapter, practiceAdapter)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_practice_list
    }

    override fun getViewModel(): PracticeListViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(PracticeListViewModel::class.java)
    }

}
