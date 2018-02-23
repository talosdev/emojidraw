package app.we.go.emojidraw.features.practice

import app.we.go.emojidraw.arch.mvp.MVP
import app.we.go.emojidraw.model.Stroke

interface PracticeContract {

    interface View : MVP.View {

        fun onGuessesReturned(strings: List<String>)

        fun setEmojiToDraw(emoji: String, emojiDescription: String)

        fun onEmojiDrawnCorrectly(emoji: String)

        fun onAllEmojisDrawn()

        fun onAllEmojisDrawnWithCheat()

        fun onTimeOut()

        fun showTooltip(position: Int)

        fun hideTooltip()

        fun showErrorMessage()
    }

    interface Presenter : MVP.Presenter<View> {

        fun onStrokeAdded(strokes: List<Stroke>)

        fun onTimeExpired()

        fun onSkip()

    }

}
