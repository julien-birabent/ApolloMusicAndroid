package julienbirabent.apollomusic.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.gms.common.SignInButton
import julienbirabent.apollomusic.R


@BindingAdapter("android:onClick")
fun bindSignInGoogleClick(button: SignInButton, method: () -> Unit) {
    button.setOnClickListener { method.invoke() }
}

@BindingAdapter("url")
fun loadImageUrl(view: ImageView, url: String?) {
    if (url != null && url != "")
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.image_placeholder)
            .into(view)
}
