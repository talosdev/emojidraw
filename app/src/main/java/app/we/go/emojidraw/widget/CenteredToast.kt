package app.we.go.emojidraw.widget

import android.content.Context
import android.support.annotation.StringRes
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast


// Toast with centered text
object CenteredToast {


    fun makeText(context: Context, @StringRes message: Int, duration: Int): Toast {
        val toast = Toast.makeText(context, message, duration)
        val layout = toast.view as LinearLayout
        if (layout.childCount > 0) {
            val tv = layout.getChildAt(0) as TextView
            tv.gravity = Gravity.CENTER_HORIZONTAL
        }
        return toast
    }
}
