package julienbirabent.apollomusic.ui.adapters.practice

import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import julienbirabent.apollomusic.data.local.model.SimpleTextItem
import julienbirabent.apollomusic.ui.adapters.base.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.base.BaseDiffCallback


class PracticeAdapter(callback: PracticeItemCallback) :
    BaseAdapter<Any, PracticeItemCallback>(callback) {

    override fun getLayoutId(position: Int, obj: Any): Int {
        return when (obj) {
            is SimpleTextItem -> R.layout.view_header_text_item
            is PracticeEntity -> R.layout.view_practice_item
            else -> R.layout.view_empty_item
        }
    }

    override fun getDiffUtilCallback(oldList: List<Any>, newList: List<Any>): BaseDiffCallback<Any>? {
        return BaseDiffCallback(
            oldList,
            newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.hashCode() == newItem.hashCode()
            },
            areContentTheSame = { oldItem, newItem ->
                oldItem == newItem
            })
    }
}