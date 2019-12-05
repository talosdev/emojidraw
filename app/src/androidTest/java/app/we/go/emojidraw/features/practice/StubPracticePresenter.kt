package app.we.go.emojidraw.features.practice

import app.we.go.emojidraw.arch.mvp.BasePresenter
import app.we.go.emojidraw.model.Stroke
import javax.inject.Inject

/**
 * Stub implementation of [PracticeContract.Presenter] that:
 * 1) notifies the view that all emojis guessed correctly on the 1st stroke
 * 2) if user has cheated (skipped), notifies the view accordingly
 * 2) calls the expired callback when it receives the signal that time expired.
 */
class StubPracticePresenter @Inject
constructor() : BasePresenter<PracticeContract.View>(), PracticeContract.Presenter {

    private var cheated: Boolean = false

    override fun onStrokeAdded(strokes: List<Stroke>) {
            if (strokes.isNotEmpty()) {
                if (cheated) {
                    view?.onAllEmojisDrawnWithCheat()
                } else {
                    view?.onAllEmojisDrawn()
                }
            }
    }

    override fun onTimeExpired() {
        view?.onTimeOut()
    }

    override fun onSkip() {
        cheated = true
    }
}
