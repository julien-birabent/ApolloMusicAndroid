package julienbirabent.apollomusic.ui.base;

import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableBoolean;
import io.reactivex.disposables.CompositeDisposable;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean isLoading = new ObservableBoolean();

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

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
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
}
