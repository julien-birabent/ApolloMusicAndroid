package julienbirabent.apollomusic.binding;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class JavaBindingAdapters {
    @BindingAdapter("app:colorScheme")
    public static void bindRefreshColor(SwipeRefreshLayout swipeRefreshLayout, int[] colorResIds) {
        swipeRefreshLayout.setColorSchemeColors(colorResIds);
    }
}
