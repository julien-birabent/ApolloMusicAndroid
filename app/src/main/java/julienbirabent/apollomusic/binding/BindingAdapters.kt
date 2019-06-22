package julienbirabent.apollomusic.binding

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.common.SignInButton


@BindingAdapter("app:setAdapter")
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.setHasFixedSize(false)
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.itemAnimator = DefaultItemAnimator()
    val divider = DividerItemDecoration(
        recyclerView.context,
        (recyclerView.layoutManager as LinearLayoutManager).orientation
    )
    ContextCompat.getDrawable(recyclerView.context, julienbirabent.apollomusic.R.drawable.line_divider)?.let {
        divider.setDrawable(
            it
        )
    }
    recyclerView.addItemDecoration(divider)
    recyclerView.adapter = adapter
}


@BindingAdapter("android:onClick")
fun bindSignInGoogleClick(button: SignInButton, method: () -> Unit) {
    button.setOnClickListener { method.invoke() }
}

@BindingAdapter("url")
fun loadImageUrl(view: ImageView, url: String?) {
    if (url != null && url != "")
        Glide.with(view.context)
            .load(url)
            .placeholder(julienbirabent.apollomusic.R.drawable.image_placeholder)
            .into(view)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("android:animatedVisibility")
fun setAnimatedVisibility(target: View, isVisible: Boolean) {
    target.visibility = if (isVisible) View.VISIBLE else View.GONE
}
