package julienbirabent.apollomusic.ui.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import julienbirabent.apollomusic.data.api.network.Resource
import julienbirabent.apollomusic.data.local.entities.ExampleEntity
import julienbirabent.apollomusic.data.repository.ExampleRepository
import julienbirabent.apollomusic.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleViewModel @Inject constructor(private val exampleRepository: ExampleRepository) :
    BaseViewModel<ExampleNavigator>() {

    val examples: LiveData<List<ExampleEntity>> = exampleRepository.getAllExamples()

    val exampleId: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onClickButton() {
        navigator?.openExample()
    }

    fun findExampleById(): LiveData<Resource<ExampleEntity>> {
        return exampleRepository.getExampleById(exampleId.value!!)
    }

    fun fetchAnExample() {
        exampleRepository.getAnExample()
    }


}