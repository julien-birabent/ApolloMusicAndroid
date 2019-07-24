package julienbirabent.apollomusic.ui.adapters.practice

import android.text.format.DateUtils
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import julienbirabent.apollomusic.data.local.model.SimpleTextItem
import julienbirabent.apollomusic.ui.adapters.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.BaseDiffCallback


class PracticeAdapter(callback: PracticeItemCallback) :
    BaseAdapter<Any, PracticeItemCallback>(callback) {

    override fun getLayoutId(position: Int, obj: Any): Int {
        return when (obj) {
            is SimpleTextItem -> if (obj.isHeader) R.layout.view_header_text_item else R.layout.view_text_line_item
            is PracticeEntity -> {
                if (DateUtils.isToday(obj.date.time)) R.layout.view_today_practice_item else R.layout.view_not_today_practice_item
            }
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