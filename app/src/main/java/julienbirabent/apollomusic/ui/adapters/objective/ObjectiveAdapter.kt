package julienbirabent.apollomusic.ui.adapters.objective

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.transition.TransitionManager
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.model.ObjectiveBundle
import julienbirabent.apollomusic.ui.adapters.ActionItem
import julienbirabent.apollomusic.ui.adapters.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.BaseDiffCallback
import julienbirabent.apollomusic.ui.adapters.DataBindingViewHolder

class ObjectiveAdapter(callback: ActionItem<ObjectiveBundle>) :
    BaseAdapter<ObjectiveBundle, ActionItem<ObjectiveBundle>>(callback) {


    override fun getLayoutId(position: Int, obj: ObjectiveBundle): Int {
        return R.layout.view_objective_item
    }

    private var expandedPosition: Int = -1

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<ObjectiveBundle, ActionItem<ObjectiveBundle>>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        val item = listItems[position]

        if (item.ex == null) {
            (holder.binding.root.findViewById(R.id.view_exercise_short_info) as View).visibility = View.GONE
        }

        val expandButton = (holder.binding.root.findViewById(R.id.expand_button) as View)
        (holder.binding.root.findViewById(R.id.delete_button) as View).setOnClickListener {
            callback?.deleteItem(listItems[position])
        }
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
        }
        expandButton.rotation = if (isExpanded) 180f else 0f
    }

    private fun getDetailsView(holder: DataBindingViewHolder<ObjectiveBundle, ActionItem<ObjectiveBundle>>): View {
        return (holder.binding.root.findViewById(R.id.objective_details_container) as View)
    }

    override fun getDiffUtilCallback(
        oldList: List<ObjectiveBundle>,
        newList: List<ObjectiveBundle>
    ): BaseDiffCallback<ObjectiveBundle> {
        return BaseDiffCallback(
            oldList,
            newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.obj == newItem.obj
            },
            areContentTheSame = { oldItem, newItem ->
                oldItem == newItem
            })
    }

    private fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 300
        animator.interpolator = LinearInterpolator()
        return animator
    }
}
