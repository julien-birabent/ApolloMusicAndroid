package julienbirabent.apollomusic.ui.adapters.exercise

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.transition.TransitionManager
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.ui.adapters.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.BaseDiffCallback
import julienbirabent.apollomusic.ui.adapters.DataBindingViewHolder
import julienbirabent.apollomusic.ui.adapters.ItemSelectionCallback


class ExerciseAdapter(callback: ItemSelectionCallback<ExerciseEntity>) :
    BaseAdapter<ExerciseEntity, ItemSelectionCallback<ExerciseEntity>>(callback) {

    private var expandedPosition: Int = -1

    override fun getLayoutId(position: Int, obj: ExerciseEntity): Int {
        return R.layout.view_exercise_selection_item
    }

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<ExerciseEntity, ItemSelectionCallback<ExerciseEntity>>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        val expandButton = (holder.binding.root.findViewById(R.id.expand_button) as View)
        val isExpanded = position == expandedPosition
        getDetailsView(holder).visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.binding.root.setOnClickListener {
            if (expandedPosition >= 0) {
                val prev = expandedPosition
                notifyItemChanged(prev)
            }
            expandedPosition = if (isExpanded) -1 else position
            TransitionManager.beginDelayedTransition(holder.binding.root as ViewGroup)
            notifyItemChanged(expandedPosition)

            val to = if (isExpanded) 0f else 180f
            createRotateAnimator(expandButton, expandButton.rotation, to).start()

            callback?.onItemSelected(listItems[position])
        }
        expandButton.rotation = if (isExpanded) 180f else 0f
    }

    private fun getDetailsView(holder: DataBindingViewHolder<ExerciseEntity, ItemSelectionCallback<ExerciseEntity>>): View {
        return (holder.binding.root.findViewById(R.id.exercise_details_container) as View)
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

    private fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 300
        animator.interpolator = LinearInterpolator()
        return animator
    }
}
