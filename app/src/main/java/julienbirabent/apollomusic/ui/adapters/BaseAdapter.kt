package julienbirabent.apollomusic.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<Model, Callback>(callback: Callback) :
    RecyclerView.Adapter<DataBindingViewHolder<Model, Callback>>() {

    protected var listItems: MutableList<Model>
    private var callback: Callback? = callback

    init {
        listItems = mutableListOf()
    }

    private fun setItems(listItems: List<Model>) {
        this.listItems = listItems.toMutableList()
        notifyDataSetChanged()
    }

    /**
     * Child classes can override this method to implement their own logic for the diffcallback
     * They can also return null if they don<t need it
     */
    open fun getDiffUtilCallback(oldList: List<Model>, newList: List<Model>): BaseDiffCallback<Model>? {
        return BaseDiffCallback(oldList, newList, areItemsTheSame = { oldItem, newItem ->
            oldItem == newItem
        }, areContentTheSame = { oldItem, newItem ->
            oldItem == newItem
        })
    }

    fun updateList(newList: List<Model>) {
        val diffUtil = getDiffUtilCallback(this.listItems, newList)
        if (diffUtil != null) {
            val result = DiffUtil.calculateDiff(diffUtil)
            result.dispatchUpdatesTo(this)
            this.listItems.clear()
            this.listItems.addAll(newList)
        } else {
            setItems(newList)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<Model, Callback> {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<Model, Callback>, position: Int) {
        holder.bind(listItems[position], callback)
    }

    override fun getItemCount(): Int {
        return if (listItems == null) 0 else listItems.count()
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, listItems[position])
    }

    protected abstract fun getLayoutId(position: Int, obj: Model): Int

    fun getViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<Model, Callback> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }
}