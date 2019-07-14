package julienbirabent.apollomusic.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.widget.AppCompatTextView
import julienbirabent.apollomusic.data.local.entities.Tablature


class TablatureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    private var guitareStrings: ArrayList<AppCompatTextView> = arrayListOf()
    private lateinit var stringsContainer: LinearLayout

    var tablature: Tablature? = emptyTablature()
        set(value) {
            field = value
            if (value != null) {
                field?.let { bindTablature(it) }
            } else this.visibility = View.GONE
        }

    init {
        initStringContainer()
        initStrings()
    }

    private fun initStringContainer() {
        stringsContainer = LinearLayout(context).apply {
            orientation = VERTICAL
            gravity = Gravity.LEFT
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            this@TablatureView.addView(this)
        }
    }

    private fun initStrings() {
        for (i in 0..5) {
            addString()
        }
        tablature?.let { bindTablature(it) }
    }

    private fun emptyTablature(): Tablature = Tablature("", "", "", "", "", "")

    private fun addString() {
        val newString = AppCompatTextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            setSingleLine()
            setHorizontallyScrolling(true)
            gravity = Gravity.START
            typeface = Typeface.MONOSPACE
            guitareStrings.add(this)
        }
        stringsContainer.addView(newString)
    }

    private fun bindTablature(tab: Tablature) {
        guitareStrings.forEachIndexed { index, textView ->
            textView.text = when (index) {
                0 -> tab.firstString
                1 -> tab.secondString
                2 -> tab.thirdString
                3 -> tab.fourthString
                4 -> tab.fifthString
                5 -> tab.sixthString
                else -> ""
            }
        }
    }

}