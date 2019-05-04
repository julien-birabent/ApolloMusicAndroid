package julienbirabent.apollomusic.ui.example

import android.arch.lifecycle.ViewModelProviders
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityExampleBinding
import julienbirabent.apollomusic.ui.base.BaseActivity
import julienbirabent.apollomusic.viewmodel.ViewModelFactory
import javax.inject.Inject

class ExampleActivity : BaseActivity<ActivityExampleBinding, ExampleViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var exampleViewModel: ExampleViewModel

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_example
    }

    override fun getViewModel(): ExampleViewModel {
        exampleViewModel = ViewModelProviders.of(this, factory).get(ExampleViewModel::class.java)
        return exampleViewModel
    }

}
