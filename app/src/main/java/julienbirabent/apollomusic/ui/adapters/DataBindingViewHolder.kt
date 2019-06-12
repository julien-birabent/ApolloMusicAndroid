package julienbirabent.apollomusic.ui.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import julienbirabent.apollomusic.BR

class DataBindingViewHolder<T, Callback>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T, callback: Callback? = null) {
        binding.setVariable(BR.item, item)
        callback?.let { binding.setVariable(BR.callback, callback) }
        binding.executePendingBindings()
    }
}