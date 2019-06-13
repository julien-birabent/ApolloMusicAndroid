package julienbirabent.apollomusic.ui.adapters

import julienbirabent.apollomusic.R
import java.util.*

class DateAdapter(callback: ActionItem<Date>) :
    BaseAdapter<Date, ActionItem<Date>>(callback) {

    override fun getLayoutId(position: Int, obj: Date): Int {
        return R.layout.view_date_row
    }

    override fun getDiffUtilCallback(oldList: List<Date>, newList: List<Date>): BaseDiffCallback<Date> {
        return BaseDiffCallback(oldList, newList, areItemsTheSame = { oldItem, newItem ->
            oldItem.hashCode() == newItem.hashCode()
        }, areContentTheSame = { oldItem, newItem ->
            oldItem.time == newItem.time
        })
    }
}