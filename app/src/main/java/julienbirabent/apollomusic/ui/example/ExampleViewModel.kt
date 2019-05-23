package julienbirabent.apollomusic.ui.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.api.network.Resource
import julienbirabent.apollomusic.data.local.entities.Example
import julienbirabent.apollomusic.data.repository.ExampleRepository
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleViewModel @Inject constructor(private val exampleRepository: ExampleRepository) :
    BaseViewModel<ExampleNavigator>() {

    val examples: LiveData<List<Example>> = exampleRepository.getAllExamples()

    val exampleId: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onClickButton() {
        navigator?.openExample()
    }

    fun findExampleById(): LiveData<Resource<Example>> {
        return exampleRepository.getExampleById(exampleId.value!!)
    }

    fun fetchAnExample() {
        exampleRepository.getAnExample()
    }


}