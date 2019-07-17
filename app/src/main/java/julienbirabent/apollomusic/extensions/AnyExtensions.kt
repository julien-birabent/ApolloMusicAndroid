package julienbirabent.apollomusic.extensions

inline fun <reified T> Any.deepCopyOf(list: List<T>): List<T> {
    val copies = mutableListOf<T>()
    for (item in list) {
        copies.add(item)
    }
    return copies
}