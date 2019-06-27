package julienbirabent.apollomusic.Utils

import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionAvailableEmitter @Inject constructor(
    var context: Context,
    var scheduler: AppSchedulerProvider
) {

    fun connectionAvailableEmitter(): Observable<Boolean> {
        return ReactiveNetwork.observeNetworkConnectivity(context)
            .flatMapSingle { ReactiveNetwork.checkInternetConnectivity() }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
    }
}