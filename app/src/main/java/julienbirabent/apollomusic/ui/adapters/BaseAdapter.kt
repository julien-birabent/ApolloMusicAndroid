package julienbirabent.apollomusic.ui.adapters

import android.telecom.Call
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<Model, Callback> :
    RecyclerView.Adapter<DataBindingViewHolder<Model, Callback>> {

    private lateinit var listItems: List<Model>
    private var callback: Callback? = null

    constructor(callback: Callback) {
        this.callback = callback
    }

    constructor() {
        listItems = emptyList()
    }

    fun setItems(listItems: List<Model>) {
        this.listItems = listItems
        notifyDataSetChanged()
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

    protected fun getViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<Model, Callback> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }
}