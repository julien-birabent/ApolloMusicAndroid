package julienbirabent.apollomusic.ui.example

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityExampleBinding
import julienbirabent.apollomusic.ui.base.BaseActivity
import javax.inject.Inject


class ExampleActivity : BaseActivity<ActivityExampleBinding, ExampleViewModel>(), ExampleNavigator {

    @Inject
    lateinit var context : Context

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel.examples.observe(this, Observer { exampleList ->
            binding.exampleDisplay.text = ""
            exampleList.forEach {
                binding.exampleDisplay.append("\n" + it.id.toString())
            }
        })

        binding.button2.setOnClickListener {
            viewModel.findExampleById().observe(this, Observer {
                Toast.makeText(context,  it.data.toString(),Toast.LENGTH_SHORT).show()
            })
        }
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
            .add(binding.root.id, fragment)
            .commit()
    }
}
