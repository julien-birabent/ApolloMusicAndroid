package julienbirabent.apollomusic.ui.adapters

import androidx.databinding.ObservableBoolean

class CheckedWrapper<Item>(var item: Item) {

    val checked = ObservableBoolean()

    init {
        checked.set(false)
    }
}