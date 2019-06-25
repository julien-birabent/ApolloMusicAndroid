package julienbirabent.apollomusic.data.repository

import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseRepository @Inject constructor() {

    @Inject
     lateinit var appExecutors: AppExecutors
    @Inject
     lateinit var scheduler: SchedulerProvider

}

