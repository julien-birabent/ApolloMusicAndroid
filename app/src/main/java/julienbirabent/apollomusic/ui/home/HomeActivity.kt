package julienbirabent.apollomusic.ui.home

import julienbirabent.apollomusic.BR
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.ui.base.BaseActivity

class HomeActivity : BaseActivity<julienbirabent.apollomusic.databinding.ActivityHomeBinding,HomeViewModel>(), HomeNavigator{

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }
}