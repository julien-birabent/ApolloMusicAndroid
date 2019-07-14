package julienbirabent.apollomusic.Utils

import androidx.lifecycle.LiveData

class DefaultLiveData<T : Any?> private constructor(val data: T) : LiveData<T>() {
    init {
        // use post instead of set since this can be created on any thread
        postValue(data)
    }

    companion object {
        fun <T> create(data: T): LiveData<T> {
            return DefaultLiveData(data)
        }
    }
}
