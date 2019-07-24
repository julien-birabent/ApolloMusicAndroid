package julienbirabent.apollomusic.ui.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import dagger.android.support.AndroidSupportInjection;
import julienbirabent.apollomusic.viewmodel.ViewModelFactory;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

@FragmentWithArgs
public abstract class BaseFragment<Binding extends androidx.databinding.ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    private BaseActivity mActivity;
    private View rootView;
    private Binding binding;
    private VM viewModel;

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
    public abstract VM getViewModel();

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        viewModel = getViewModel();
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hideToolbar(true);
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        hideToolbar(false);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mActivity.setRequestedOrientation(getDefaultScreenOrientation());
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBindingVariables(binding);
        binding.setLifecycleOwner(this);
        if (this instanceof UINavigator) {
            viewModel.setNavigator(this);
        }
        binding.executePendingBindings();
        mActivity.setRequestedOrientation(getDefaultScreenOrientation());
    }

    protected void setBindingVariables(@NotNull androidx.databinding.ViewDataBinding binding) {
        this.binding.setVariable(getBindingVariable(), viewModel);
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public Binding getViewDataBinding() {
        return binding;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    protected ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }

    protected int getDefaultScreenOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private void hideToolbar(Boolean hideIt) {
        if (this instanceof HideToolbarCallback) {
            getBaseActivity().hideToolbar(hideIt);
            ((HideToolbarCallback) this).onHideToolbar(!hideIt);
        }
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
