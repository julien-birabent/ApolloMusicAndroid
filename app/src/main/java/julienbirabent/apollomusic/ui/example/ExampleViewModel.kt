package julienbirabent.apollomusic.ui.example

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject

class ExampleViewModel @Inject constructor() : BaseViewModel<ExampleNavigator>(){

    fun onClickButton(){
        navigator?.openExample()
    }

}