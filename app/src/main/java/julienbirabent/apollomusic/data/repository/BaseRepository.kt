package julienbirabent.apollomusic.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
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

    @Inject
    lateinit var context: Context

    @SuppressLint("CheckResult")
    fun onConnectionAvailableEmitter(): Observable<Boolean>? {
        return ReactiveNetwork.observeNetworkConnectivity(context)
            .flatMapSingle { ReactiveNetwork.checkInternetConnectivity() }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
    }

}

