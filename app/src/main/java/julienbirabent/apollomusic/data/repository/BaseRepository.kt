package julienbirabent.apollomusic.data.repository

import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class BaseRepository{

    @Inject
    protected lateinit var  appExecutors: AppExecutors
    @Inject
    protected lateinit var  scheduler: SchedulerProvider

}

