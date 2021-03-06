package app.we.go.emojidraw.features.practice

import app.we.go.emojidraw.api.EmojiDetectionProvider
import app.we.go.emojidraw.arch.di.ActivityScope
import app.we.go.emojidraw.arch.mvp.BasePresenter
import app.we.go.emojidraw.data.EmojiToDraw
import app.we.go.emojidraw.data.EmojiToDrawProvider
import app.we.go.emojidraw.model.Stroke
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
open class PracticePresenter @Inject
constructor(private val detectionProvider: EmojiDetectionProvider,
            private val emojiToDrawProvider: EmojiToDrawProvider,
            private val nEmojis: Int) : BasePresenter<PracticeContract.View>(), PracticeContract.Presenter {

    private var emojisToDraw: List<EmojiToDraw> = emptyList()
    private var currentEmojiIndex: Int = 0
    private var hasSkipped: Boolean = false
    private var won: Boolean = false


    override fun initialize() {
        super.initialize()
        emojisToDraw = emojiToDrawProvider.provide(nEmojis)
        updateEmojiToDraw()
    }

    private fun updateEmojiToDraw() {
        val (description, emoji) = emojisToDraw[currentEmojiIndex]
        view?.setEmojiToDraw(emoji, description)
    }

    override fun onStrokeAdded(strokes: List<Stroke>) {
        disposables.add(
            detectionProvider.getEmojis(strokes)
                // TODO move to provider
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ processEmojiGuesses(it) },
                    { onError(it) })
        )

    }

    private fun processEmojiGuesses(emojis: List<String>) {
        view?.onGuessesReturned(emojis)
        if (!emojis.isEmpty() && currentEmojiIndex < emojisToDraw.size) {
            val index = emojis.indexOf(emojisToDraw[currentEmojiIndex].emoji)
            if (index > 0) { // if 0, user has won, so no point in showing the tooltip
                // TODO only show the tooltip once or twice
                view?.showTooltip(index)
            } else {
                view?.hideTooltip()
            }

            val currentEmoji = emojisToDraw[currentEmojiIndex].emoji

            if (emojis[0] == currentEmoji) {
                disposables.clear() // dispose of all pending requests
                view?.onEmojiDrawnCorrectly(currentEmoji)
                handleNextEmoji()
            }
        } else {
            view?.hideTooltip()
        }
    }

    private fun onError(throwable: Throwable) {
        Timber.e(throwable)
        view?.showErrorMessage()
    }


    /**
     * @return `true` if there is a next emoji, false if we're finished
     */
    open fun handleNextEmoji(): Boolean {
        currentEmojiIndex++

        return if (currentEmojiIndex == emojisToDraw.size) {
            won = true
            if (hasSkipped) {
                view?.onAllEmojisDrawnWithCheat()
            } else
                view?.onAllEmojisDrawn()
            false
        } else {
            updateEmojiToDraw()
            true
        }
    }


    override fun onTimeExpired() {
        if (!won) {
            view?.onTimeOut()
        }
    }

    override fun onSkip() {
        if (currentEmojiIndex < emojisToDraw.size) {
            hasSkipped = true
            handleNextEmoji()
        }
    }

}
