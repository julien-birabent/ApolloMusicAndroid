package julienbirabent.apollomusic.data.repository

import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import julienbirabent.apollomusic.Utils.ConnectionAvailableEmitter
import julienbirabent.apollomusic.app.ApolloMusicApplication
import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.SchedulerProvider
import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var scheduler: SchedulerProvider
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var connectionEmitter: ConnectionAvailableEmitter

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        ApolloMusicApplication.applicationComponent()?.inject(repository = this)

    }

    fun connectionAvailableEmitter(): Observable<Boolean> {
        return connectionEmitter.connectionAvailableEmitter()
    }
}

