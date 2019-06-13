package julienbirabent.apollomusic.ui.adapters

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallback<Model>(
    private var oldList: List<Model>,
    private var newList: List<Model>,
    private var areItemTheSame: ((Model) -> Any)
) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemTheSame(oldList[oldItemPosition]) == areItemTheSame(newList[newItemPosition])
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}