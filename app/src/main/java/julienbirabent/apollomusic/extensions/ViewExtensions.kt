package julienbirabent.apollomusic.extensions

import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import julienbirabent.apollomusic.R


fun View.pxToDp(px: Int): Float {
    val resources = this.context.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun View.dpToPx(dp: Float): Float {
    return (dp * context.resources.displayMetrics.density)
}

fun Snackbar.error() {
    val message = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    message.setTextColor(context.getColor(R.color.white))
    val snackViewButton = view.findViewById(com.google.android.material.R.id.snackbar_action) as Button
    snackViewButton.setTextColor(context.getColor(R.color.white))
    view.setBackgroundColor(context.getColor(R.color.saumon))
    show()
}

fun Snackbar.success() {
    val message = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    message.setTextColor(context.getColor(R.color.white))
    val snackViewButton = view.findViewById(com.google.android.material.R.id.snackbar_action) as Button
    snackViewButton.setTextColor(context.getColor(R.color.white))
    view.setBackgroundColor(context.getColor(R.color.green_fresh_herb))
    show()
}

fun Snackbar.info() {
    val message = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    message.setTextColor(context.getColor(R.color.white))
    val snackViewButton = view.findViewById(com.google.android.material.R.id.snackbar_action) as Button
    snackViewButton.setTextColor(context.getColor(R.color.white))
    view.setBackgroundColor(context.getColor(R.color.light_gray))
    show()
}