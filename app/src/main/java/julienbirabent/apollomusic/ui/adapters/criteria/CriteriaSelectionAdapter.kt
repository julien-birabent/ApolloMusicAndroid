package julienbirabent.apollomusic.ui.adapters.criteria

import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.ui.adapters.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.BaseDiffCallback
import julienbirabent.apollomusic.ui.adapters.CheckedWrapper
import julienbirabent.apollomusic.ui.adapters.ItemSelectionCallback

class CriteriaSelectionAdapter(callback: ItemSelectionCallback<CriteriaEntity>) :
    BaseAdapter<CheckedWrapper<CriteriaEntity>, ItemSelectionCallback<CriteriaEntity>>(callback) {


    override fun getLayoutId(position: Int, obj: CheckedWrapper<CriteriaEntity>): Int {
        return R.layout.view_criteria_selection_row
    }

    override fun getDiffUtilCallback(
        oldList: List<CheckedWrapper<CriteriaEntity>>,
        newList: List<CheckedWrapper<CriteriaEntity>>
    ): BaseDiffCallback<CheckedWrapper<CriteriaEntity>>? {
        return BaseDiffCallback(
            oldList,
            newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.item.id == newItem.item.id
            },
            areContentTheSame = { oldItem, newItem ->
                oldItem.item == newItem.item
            })
    }
}