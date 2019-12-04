package app.we.go.emojidraw.widget

import android.content.Context
import androidx.annotation.StringRes
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

// Toast with centered text
// TODO convert to an extension function on Activity?
object CenteredToast {

    fun show(context: Context, @StringRes message: Int, duration: Int) {
        val toast = Toast.makeText(context, message, duration)
        val layout = toast.view as LinearLayout
        (layout.getChildAt(0) as TextView?)?.apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
        toast.show()
    }
}
