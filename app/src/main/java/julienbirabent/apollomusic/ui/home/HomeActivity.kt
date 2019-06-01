package julienbirabent.apollomusic.ui.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityHomeBinding
import julienbirabent.apollomusic.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setupNavigation()
        setSupportActionBar(binding.toolbar)
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.mainNavigationFragment)
        setupActionBarWithNavController(navController)
        binding.navigation.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp() = findNavController(R.id.mainNavigationFragment).navigateUp()

    //<editor-fold desc="Base activity heritage">
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }
    //</editor-fold>
}