package julienbirabent.apollomusic.ui.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeListBinding
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.ui.example.ExampleViewModel

class PracticeListFragment : BaseFragment<FragmentPracticeListBinding, PracticeListViewModel>(), PracticeListNavigator
{

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
