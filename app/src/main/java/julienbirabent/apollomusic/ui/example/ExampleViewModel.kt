package julienbirabent.apollomusic.ui.example

import android.util.Log
import androidx.lifecycle.LiveData
import julienbirabent.apollomusic.data.local.entities.Example
import julienbirabent.apollomusic.data.repository.ExampleRepository
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleViewModel @Inject constructor(private val exampleRepository: ExampleRepository) :
    BaseViewModel<ExampleNavigator>() {

    val examples: LiveData<List<Example>> = exampleRepository.getAllExamples()

    fun onClickButton() {
        navigator?.openExample()
    }

    fun pingServer() {
        exampleRepository.getAnExample()
    }
}