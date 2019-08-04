package julienbirabent.apollomusic.ui.adapters.objective

import android.animation.ObjectAnimator
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.transition.TransitionManager
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.model.ObjectiveBundle
import julienbirabent.apollomusic.ui.adapters.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.BaseDiffCallback
import julienbirabent.apollomusic.ui.adapters.DataBindingViewHolder
import java.util.*

class ObjectiveDetailAdapter(callback: ObjectiveDetailsItemCallback? = null) :
    BaseAdapter<ObjectiveBundle, ObjectiveDetailsItemCallback>(callback) {

    private var expandedPosition: Int = -1
    lateinit var currentPracticeDate: Date

    override fun getLayoutId(position: Int, obj: ObjectiveBundle): Int {
        return R.layout.view_objective_details_item
    }

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<ObjectiveBundle, ObjectiveDetailsItemCallback>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        val item = listItems[position]
        val displayPosition = (position + 1).toString()

        holder.binding.root.findViewById<TextView>(R.id.done_header).apply {
            visibility = View.VISIBLE
            if (item.obj.done) {
                text = context.getText((R.string.done))
                setTextColor(context.getColor(R.color.green_fresh_herb))
            } else {
                text = context.getText(R.string.to_do)
                setTextColor(context.getColor(R.color.colorAccent))
            }
        }

        holder.binding.root.findViewById<Button>(R.id.start_exercise).apply {
            visibility = if (DateUtils.isToday(currentPracticeDate.time)) {
                View.VISIBLE
            } else View.GONE
        }

        (holder.binding.root.findViewById<AppCompatTextView>(R.id.objective_header)).apply {
            text = String.format(context.resources.getString(R.string.objective_header), displayPosition)
        }
        if (item.ex == null) {
            (holder.binding.root.findViewById(R.id.view_exercise_short_info) as View).visibility = View.GONE
        }

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
        }
        expandButton.rotation = if (isExpanded) 180f else 0f
    }

    private fun getDetailsView(holder: DataBindingViewHolder<ObjectiveBundle, ObjectiveDetailsItemCallback>): View {
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