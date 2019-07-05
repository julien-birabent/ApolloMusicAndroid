package julienbirabent.apollomusic.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
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

    fun connectionAvailableEmitter(): Observable<Connectivity> {
        return ReactiveNetwork
            .observeNetworkConnectivity(context)
            .subscribeOn(scheduler.io())
            .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
            .filter(ConnectivityPredicate.hasType(ConnectivityManager.TYPE_WIFI))
            .observeOn(scheduler.ui())
    }
}