package julienbirabent.apollomusic.extensions

import android.util.DisplayMetrics
import android.view.View


fun View.pxToDp(px: Int): Float {
    val resources = this.context.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun View.dpToPx(dp: Float): Float {
    return (dp * context.resources.displayMetrics.density)
}