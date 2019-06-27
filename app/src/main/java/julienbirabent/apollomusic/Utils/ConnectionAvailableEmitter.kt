package julienbirabent.apollomusic.Utils

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import julienbirabent.apollomusic.app.ApolloMusicApplication
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
object ConnectionAvailableEmitter {

    @set:Inject
    lateinit var scheduler: dagger.Lazy<AppSchedulerProvider>

    fun connectionAvailableEmitter(): Observable<Boolean> {
        return ReactiveNetwork.observeNetworkConnectivity(ApolloMusicApplication.applicationContext())
            .flatMapSingle { ReactiveNetwork.checkInternetConnectivity() }
            .subscribeOn(scheduler.get().io())
            .observeOn(scheduler.get().ui())
    }
}