package julienbirabent.apollomusic.ui.adapters.exercise

import android.animation.ObjectAnimator
import android.util.SparseBooleanArray
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter
import com.github.aakira.expandablelayout.ExpandableLinearLayout
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import julienbirabent.apollomusic.ui.adapters.BaseAdapter
import julienbirabent.apollomusic.ui.adapters.BaseDiffCallback
import julienbirabent.apollomusic.ui.adapters.DataBindingViewHolder
import julienbirabent.apollomusic.ui.adapters.ItemSelectionCallback


class ExerciseAdapter(callback: ItemSelectionCallback<ExerciseEntity>) :
    BaseAdapter<ExerciseEntity, ItemSelectionCallback<ExerciseEntity>>(callback) {

    private val expandState = SparseBooleanArray()

    init {
        listItems.forEachIndexed { index, _ ->
            expandState.append(index, false)
        }
    }

    override fun getLayoutId(position: Int, obj: ExerciseEntity): Int {

        return R.layout.view_exercise_selection_item
    }

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<ExerciseEntity, ItemSelectionCallback<ExerciseEntity>>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.setIsRecyclable(false)
        val expandButton = holder.binding.root.findViewById(R.id.expand_button) as View
        val expandableLayout = holder.binding.root.findViewById(R.id.expandable_layout) as ExpandableLinearLayout
        with(expandableLayout) {
            setInRecyclerView(true)
            isExpanded = expandState.get(position)
            setListener(object : ExpandableLayoutListenerAdapter() {
                override fun onPreOpen() {
                    super.onPreOpen()
                    createRotateAnimator(expandButton, 0f, 180f).start()
                    expandState.put(position, true)
                }

                override fun onPreClose() {
                    super.onPreClose()
                    createRotateAnimator(expandButton, 0f, 180f).start()
                    expandState.put(position, false)
                }
            })
        }

        expandButton.rotation = if (expandState.get(position)) 180f else 0f
        expandButton.setOnClickListener {
            expandableLayout.toggle()
        }

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

    fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 300
        animator.interpolator = AnticipateOvershootInterpolator()
        return animator
    }
}
