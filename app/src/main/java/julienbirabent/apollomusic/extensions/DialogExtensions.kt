package julienbirabent.apollomusic.extensions

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.databinding.ViewInputDialogBinding

/**
 * Le boolean se sortie de la methode indique si le dialog doit dismiss ou non.
 * onPositive: (input: String) -> Boolean
 */
fun Context.showInputDialog(
    title: String,
    message: String,
    onPositive:
        (input: String) -> Boolean,
    onCancel: () -> Unit = {}
) {
    val inflater = LayoutInflater.from(this)
    val titleView = inflater.inflate(R.layout.view_dialog_title, null) as TextView
    titleView.text = title
    titleView.setTextColor(getColor(R.color.text_header_1))

    val dialogContentView = DataBindingUtil.inflate<ViewInputDialogBinding>(
        inflater,
        R.layout.view_input_dialog,
        null,
        false
    )
    dialogContentView.message.text = message

    with(AlertDialog.Builder(this, R.style.AlertDialogTheme_White)) {
        setCustomTitle(titleView)
        setView(dialogContentView.root)
        setNegativeButton(R.string.dialog_cancel) { dialogInterface: DialogInterface, _ ->
            onCancel()
            dialogInterface.dismiss()
        }
        setNeutralButton(
            R.string.dialog_done
        ) { dialogInterface, _ ->
            onPositive(dialogContentView.input.text.toString()).also {
                if (it) {
                    dialogInterface.dismiss()
                }
            }
        }
        create()
    }.apply {
        show()
        setCanceledOnTouchOutside(true)
        with(getButton(AlertDialog.BUTTON_NEUTRAL)) {
            setOnClickListener {
                onPositive(dialogContentView.input.text.toString()).also {
                    if (it) {
                        dismiss()
                    }
                }
            }
        }
    }

}