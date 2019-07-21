package julienbirabent.apollomusic.ui.base;

import android.util.Log;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean isLoading = new ObservableBoolean();

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> navigator;

    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public N getNavigator() {
        return navigator.get();
    }

    public void setNavigator(N navigator) {
        this.navigator = new WeakReference<>(navigator);
    }
}
