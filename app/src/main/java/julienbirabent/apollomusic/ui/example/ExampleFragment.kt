package julienbirabent.apollomusic.ui.example

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.Observer
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.FragmentExampleBinding
import julienbirabent.apollomusic.ui.base.BaseFragment
import julienbirabent.apollomusic.viewmodel.ViewModelFactory
import javax.inject.Inject
import javax.inject.Named

@FragmentWithArgs
class ExampleFragment: BaseFragment<FragmentExampleBinding, ExampleViewModel>() {

    @Inject
    @field:Named("FragArgs")
    lateinit var args :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeText(baseActivity,args,Toast.LENGTH_LONG).show()

        viewModel.examples.observe(viewLifecycleOwner, Observer { it ->
            it.forEach {
                Log.e("updating examples list", it.toString())
            }
        })
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_example
    }

    override fun getViewModel(): ExampleViewModel {
                return ViewModelProviders.of(baseActivity).get(ExampleViewModel::class.java)
    }

}