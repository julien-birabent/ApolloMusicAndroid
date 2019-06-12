package julienbirabent.apollomusic.ui.adapters

interface ActionItem<T>{

    fun modifyItem(item : T)

    fun deleteItem(item: T)
}