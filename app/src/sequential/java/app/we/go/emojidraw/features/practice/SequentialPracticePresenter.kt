package app.we.go.emojidraw.features.practice

import app.we.go.emojidraw.api.EmojiDetectionProvider
import app.we.go.emojidraw.arch.di.ActivityScope
import app.we.go.emojidraw.arch.di.PracticeDuration
import app.we.go.emojidraw.data.EmojiToDrawProvider
import javax.inject.Inject


/**
 * This is al alternative implementation of [PracticeContract.Presenter]
 * for the `sequential` that is just meant to be used in the testing/development phase.
 * No point to get fancy with real decorators and stuff, plain old inheritance is OK.
 */
@ActivityScope
class SequentialPracticePresenter @Inject
constructor(detectionProvider: EmojiDetectionProvider,
            emojiToDrawProvider: EmojiToDrawProvider,
            @PracticeDuration duration: Int,
            private val sharedPrefsHelper: SequentialSharedPrefsHelper) :
    PracticePresenter(detectionProvider, emojiToDrawProvider, duration) {

    override fun handleNextEmoji(): Boolean {
        sharedPrefsHelper.increaseCurrentIndex()
        return super.handleNextEmoji()
    }


}
