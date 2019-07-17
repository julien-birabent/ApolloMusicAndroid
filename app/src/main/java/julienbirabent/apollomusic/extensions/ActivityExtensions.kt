package julienbirabent.apollomusic.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import julienbirabent.apollomusic.R

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)

}

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)

}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

fun Activity.makeSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG,
    actionText: String = "",
    action: (() -> Unit) = {}
): Snackbar {

    val anchorView = findViewById<CoordinatorLayout>(R.id.coordinator_layout) ?: findViewById(R.id.content)
    return Snackbar.make(anchorView, message, duration).apply {
        setAction(actionText) {
            action()
            dismiss()
        }
    }
}