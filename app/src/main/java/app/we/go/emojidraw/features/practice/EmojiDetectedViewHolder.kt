package app.we.go.emojidraw.features.practice

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

class EmojiDetectedViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    private val emojiView: TextView = itemView as TextView
    private val pulseAnimation: ObjectAnimator

    init {
        pulseAnimation = ObjectAnimator.ofPropertyValuesHolder(
                emojiView,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f))
        pulseAnimation.duration = 310

        pulseAnimation.repeatCount = ObjectAnimator.INFINITE
        pulseAnimation.repeatMode = ObjectAnimator.REVERSE
    }


    fun bind(emoji: String, isTheOne: Boolean) {
        emojiView.text = emoji

        // show a pulse animation on the element that represents the correct emoji
        if (isTheOne) {
            pulseAnimation.start()
        } else {
            pulseAnimation.cancel()
        }
    }
}
