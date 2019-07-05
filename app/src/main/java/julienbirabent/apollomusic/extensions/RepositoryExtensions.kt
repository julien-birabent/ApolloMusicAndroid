package julienbirabent.apollomusic.extensions

import julienbirabent.apollomusic.data.repository.BaseRepository

fun BaseRepository.dbExec(block: () -> Unit) {
    appExecutors.diskIO().execute {
        block()
    }
}