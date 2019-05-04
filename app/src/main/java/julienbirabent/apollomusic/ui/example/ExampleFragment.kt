package julienbirabent.apollomusic.ui.example

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentExampleBinding
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.viewmodel.ViewModelFactory
import javax.inject.Inject
@FragmentWithArgs
class ExampleFragment : BaseFragment<FragmentExampleBinding, ExampleViewModel>() {
    @Inject
    lateinit var factory: ViewModelFactory

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
       return R.layout.fragment_example
    }

    override fun getViewModel(): ExampleViewModel {
        return  ViewModelProviders.of(this, factory).get(ExampleViewModel::class.java)
    }

}