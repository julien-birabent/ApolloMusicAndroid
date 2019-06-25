package julienbirabent.apollomusic.ui.home

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ActivityHomeBinding
import julienbirabent.apollomusic.ui.base.BaseActivity
import java.security.MessageDigest


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
        printHashKey(this)
    }

    private fun setupNavigation() {
        val navController = findNavController(julienbirabent.apollomusic.R.id.mainNavigationFragment)
        setupActionBarWithNavController(navController)
        binding.navigation.setupWithNavController(navController)
    }

    fun printHashKey(context: Context) {
        try {
            val info =
                context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("AppLog", "key:$hashKey=")
            }
        } catch (e: Exception) {
            Log.e("AppLog", "error:", e)
        }

    }

    fun hideBottomNavigation(hide: Boolean) {
        when (hide) {
            true -> binding.navigation.visibility = View.GONE
            false -> binding.navigation.visibility = View.VISIBLE
        }
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