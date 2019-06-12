package julienbirabent.apollomusic.ui.practice.create

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeCreateBinding
import julienbirabent.apollomusic.ui.adapters.DateAdapter
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.home.HomeActivity

class PracticeCreateFragment : BaseFragment<FragmentPracticeCreateBinding, PracticeCreateViewModel>(),
    PracticeCreateNavigator {

    private lateinit var dateAdapter: DateAdapter

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is HomeActivity){
            activity.hideBottomNavigation(true)
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (activity is HomeActivity){
            (activity as HomeActivity).hideBottomNavigation(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.getPracticesDates().observe(viewLifecycleOwner, Observer {
            dateAdapter.setItems(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.create_practice_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        dateAdapter = DateAdapter(viewModel.dateActionItemCallback)
        binding.setVariable(BR.dateAdapter, dateAdapter)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_practice_create
    }

    override fun getViewModel(): PracticeCreateViewModel {
        return ViewModelProviders.of(baseActivity).get(PracticeCreateViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PracticeCreateFragment()
    }
}
