package julienbirabent.apollomusic.ui.adapters.exercise

import android.view.ViewGroup
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.ui.adapters.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.BaseDiffCallback
import julienbirabent.apollomusic.ui.adapters.DataBindingViewHolder
import julienbirabent.apollomusic.ui.adapters.ItemSelectionCallback

class ExerciseAdapter(callback: ItemSelectionCallback<ExerciseEntity>) :
    BaseAdapter<ExerciseEntity, ItemSelectionCallback<ExerciseEntity>>(callback) {

    override fun getLayoutId(position: Int, obj: ExerciseEntity): Int {

        return R.layout.view_exercise_selection_item
    }

    override fun getViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<ExerciseEntity, ItemSelectionCallback<ExerciseEntity>> {
        return super.getViewHolder(parent, viewType)
    }

    override fun getDiffUtilCallback(
        oldList: List<ExerciseEntity>,
        newList: List<ExerciseEntity>
    ): BaseDiffCallback<ExerciseEntity> {
        return BaseDiffCallback(
            oldList,
            newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.id == newItem.id
            },
            areContentTheSame = { oldItem, newItem ->
                oldItem.title == newItem.title
            })
    }


}
