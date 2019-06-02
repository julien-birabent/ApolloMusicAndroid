package julienbirabent.apollomusic.ui.practice.create

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.ViewModelProviders
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentPracticeCreateBinding
import julienbirabent.apollomusic.ui.base.BaseFragment

class PracticeCreateFragment : BaseFragment<FragmentPracticeCreateBinding, PracticeCreateViewModel>(),
    PracticeCreateNavigator {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.create_practice_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)

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
