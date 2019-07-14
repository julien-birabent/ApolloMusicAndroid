package julienbirabent.apollomusic.ui.practice.list

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.Tablature
import julienbirabent.apollomusic.databinding.FragmentPracticeListBinding
import julienbirabent.apollomusic.ui.base.BaseFragment

class PracticeListFragment : BaseFragment<FragmentPracticeListBinding, PracticeListViewModel>(),
    PracticeListNavigator {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.createPractice.setOnClickListener {
            findNavController().navigate(R.id.action_actionHome_to_practiceCreateFragment)
        }

    }

    override fun setBindingVariables(binding: ViewDataBinding) {
        super.setBindingVariables(binding)
        val tab = Tablature(
            "----------------------------5---------------------------------------------------------------------------------------------"
            ,
            "-------------------------------------------------------6------------------------------------------------------------------",
            "--------------------------------------------------------7-----------------------------------------------------------------",
            "--------------------------------------------------------------------------------------------------------------------------",
            "--------------------------------------------------------------------------------------------------------------------------",
            "----------------------------------------------------------------8---------------------------------------------------------"
        )
        binding.setVariable(BR.tablature, tab)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_practice_list
    }

    override fun getViewModel(): PracticeListViewModel {
        return ViewModelProviders.of(baseActivity).get(PracticeListViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PracticeListFragment()
    }
}
