package app.we.go.emojidraw.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

import app.we.go.emojidraw.R
import app.we.go.emojidraw.R.drawable.emoji
import kotlinx.android.synthetic.main.popup_generic.view.*

class CutePopup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {


    init {
        View.inflate(context, R.layout.popup_generic, this)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CutePopup,
            0, 0)
        val backgroundDrawable = typedArray.getDrawable(R.styleable.CutePopup_cp_backgroundDrawable)
        val firstLine = typedArray.getString(R.styleable.CutePopup_cp_firstLine)
        val secondLine = typedArray.getString(R.styleable.CutePopup_cp_secondLine)
        val emoji = typedArray.getString(R.styleable.CutePopup_cp_emoji)

        typedArray.recycle()

        backgroundDrawable?.let {
            popupContainer.background = it
        }

        if (!TextUtils.isEmpty(firstLine)) {
            firstLineTextView.text = firstLine
        }
        if (!TextUtils.isEmpty(secondLine)) {
            secondLineTextView.visibility = View.VISIBLE
            secondLineTextView.text = secondLine
        }
        if (!TextUtils.isEmpty(emoji)) {
            emojiTextView.text = emoji
        }
    }

}
