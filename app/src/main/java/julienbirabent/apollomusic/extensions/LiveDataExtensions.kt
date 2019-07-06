package julienbirabent.apollomusic.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.add(item: T) {
    val updatedItems = (this.value as ArrayList)
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<List<T>>.remove(item: T) {
    val updatedItems = (this.value as ArrayList)
    updatedItems.remove(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<List<T>>.clear() {
    val updatedItems = (this.value as ArrayList)
    updatedItems.clear()
    this.value = updatedItems
}