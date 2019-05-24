package julienbirabent.apollomusic.Utils

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.asSingleEvent(): LiveData<T> {
    val result = SingleLiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}