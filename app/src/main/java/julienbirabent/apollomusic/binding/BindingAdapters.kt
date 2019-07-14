package julienbirabent.apollomusic.binding

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.google.android.gms.common.SignInButton
import android.widget.TextView
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.appcompat.widget.AppCompatEditText
import android.widget.EditText
import androidx.databinding.BindingConversion
import androidx.recyclerview.widget.DividerItemDecoration


@BindingConversion
fun intToStr(value: Int?): String {
    return value?.toString() ?: ""
}

@InverseBindingAdapter(attribute = "android:text")
fun captureIntValue(view: AppCompatEditText): Int? {
    var value: Long = 0
    try {
        value = Integer.parseInt(view.text.toString()).toLong()
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }

    return value.toInt()
}

@BindingAdapter("app:setAdapter")
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.setHasFixedSize(false)
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.adapter = adapter
    recyclerView.addItemDecoration(
        DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
    )
}


@BindingAdapter("android:onClick")
fun bindSignInGoogleClick(button: SignInButton, method: () -> Unit) {
    button.setOnClickListener { method.invoke() }
}

@BindingAdapter("android:onClick")
fun bindCardViewOnClick(view: CardView, method: () -> Unit) {
    view.setOnClickListener { method.invoke() }
}

/*@BindingAdapter("android:onClick")
fun bindSignInGoogleClick(view: View, method: () -> Unit) {
    view.setOnClickListener { method.invoke() }
}*/

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

@BindingAdapter(value = ["animatedVisibility", "transition", "transitionTime"], requireAll = false)
fun setAnimatedVisibilityWithTransition(
    target: View,
    animatedVisibility: Boolean = false,
    transition: String?,
    transitionTime: Int = 500
) {

    if (transition != null) {
        val transitionDesired = when (transition) {
            "fade" -> Fade()
            "slide_from_bottom" -> Slide(Gravity.BOTTOM)
            else -> Fade()
        }
        transitionDesired.duration = transitionTime.toLong()
        TransitionManager.beginDelayedTransition(target.rootView as ViewGroup, transitionDesired)
    } else {
        TransitionManager.beginDelayedTransition(target.rootView as ViewGroup)
    }
    target.visibility = if (animatedVisibility) View.VISIBLE else View.GONE
}

