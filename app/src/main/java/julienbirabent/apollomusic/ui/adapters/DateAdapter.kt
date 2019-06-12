package julienbirabent.apollomusic.ui.adapters

import julienbirabent.apollomusic.R
import java.util.*

class DateAdapter(callback: ActionItem<Date>) :
    BaseAdapter<Date, ActionItem<Date>>(callback) {

    override fun getLayoutId(position: Int, obj: Date): Int {
        return R.layout.view_date_row
    }
}