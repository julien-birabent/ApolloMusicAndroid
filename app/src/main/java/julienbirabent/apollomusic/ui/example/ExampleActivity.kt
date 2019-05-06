package julienbirabent.apollomusic.ui.example

import android.content.Context
import android.os.Bundle
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityExampleBinding
import julienbirabent.apollomusic.ui.base.BaseActivity
import javax.inject.Inject
import javax.inject.Named


class ExampleActivity : BaseActivity<ActivityExampleBinding, ExampleViewModel>(), ExampleNavigator {

    @Inject
    @field:Named("ApplicationContext")
    lateinit var context : Context

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModel.navigator = this

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_example
    }

    override fun getViewModelClass(): Class<ExampleViewModel> {
        return ExampleViewModel::class.java
    }

    override fun openExample() {
        // Using the generated Builder
        val fragment = ExampleFragmentBuilder()
            .build()

        // Fragment Transaction
        supportFragmentManager
            .beginTransaction()
            .add(viewDataBinding.root.id, fragment)
            .commit()
    }
}
