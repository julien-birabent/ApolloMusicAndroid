package julienbirabent.apollomusic.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import julienbirabent.apollomusic.R;
import julienbirabent.apollomusic.Utils.NetworkUtils;
import julienbirabent.apollomusic.thread.SchedulerProvider;
import julienbirabent.apollomusic.viewmodel.ViewModelFactory;

import javax.inject.Inject;
import java.util.Objects;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback, HasSupportFragmentInjector {

    @Inject
    SchedulerProvider schedulerProvider;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    @Inject
    public ViewModelFactory viewModelFactory;

    protected T binding;
    public V viewModel;

    protected CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    protected abstract Class<V> getViewModelClass();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        Log.d("Activity launched : " + this.getLocalClassName(), "event : onCreate");
        performDataBinding();
    }

    public T getBinding() {
        return binding;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideToolbar(Boolean hideIt) {
        if (hideIt) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        } else Objects.requireNonNull(getSupportActionBar()).show();
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        //startActivity(LoginActivity.newIntent(this));
        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    private void performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        this.viewModel = viewModel == null ?
                ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass()) : viewModel;
        binding.setVariable(getBindingVariable(), viewModel);
        if (this instanceof UINavigator) {
            viewModel.setNavigator(this);
        }
        binding.executePendingBindings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposable.add(ReactiveNetwork.observeNetworkConnectivity(this)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        connectivity -> {
                            switch (connectivity.state()) {
                                case DISCONNECTED:
                                case SUSPENDED:
                                case DISCONNECTING:
                                case UNKNOWN:
                                    showErrorDialog(getResources().getString(R.string.dialog_error_no_internet_title),
                                            getResources().getString(R.string.dialog_error_no_internet_message));
                                    break;
                                case CONNECTED:
                                case CONNECTING:
                                    break;
                            }
                        },
                        throwable -> {

                        }
                ));
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.dispose();
    }

    public void showErrorDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme_White).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}

