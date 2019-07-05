package julienbirabent.apollomusic.Utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import julienbirabent.apollomusic.thread.SchedulerProvider

class StateLiveData<T>(
    private var schedulerProvider: SchedulerProvider,
    private val observable: Observable<T>
) : MutableLiveData<StateData<T>>() {

    private var disposable = CompositeDisposable()

    override fun onActive() {
        super.onActive()

        disposable.add(
            observable
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    postLoading()
                    Log.d("State live data test", "postLoading")
                }
                .doOnTerminate {
                    postComplete()
                    Log.d("State live data test", "postComplete")
                }
                .doFinally {
                    postComplete()
                }
                .doOnComplete {
                    postComplete()
                }
                .subscribe({
                    postSuccess(it)
                    Log.d("State live data test", "postSuccess with : " + it.toString())
                }, {
                    postError(it)
                    Log.d("State live data test", "postError with : " + it.toString())
                })
        )
    }

    override fun onInactive() {
        super.onInactive()
        disposable.clear()
    }

    /**
     * Use this to put the Data on a LOADING Status
     */
    private fun postLoading() {
        postValue(StateData<T>().loading())
    }

    /**
     * Use this to put the Data on a ERROR DataStatus
     *
     * @param throwable the error to be handled
     */
    private fun postError(throwable: Throwable) {
        postValue(StateData<T>().error(throwable))
    }

    /**
     * Use this to put the Data on a SUCCESS DataStatus
     *
     * @param data
     */
    private fun postSuccess(data: T) {
        postValue(StateData<T>().success(data))
    }

    /**
     * Use this to put the Data on a COMPLETE DataStatus
     */
    private fun postComplete() {
        postValue(StateData<T>().complete())
    }

}