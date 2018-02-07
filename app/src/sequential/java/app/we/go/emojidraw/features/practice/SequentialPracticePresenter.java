package app.we.go.emojidraw.features.practice;

import javax.inject.Inject;

import app.we.go.emojidraw.api.EmojiDetectionProvider;
import app.we.go.emojidraw.arch.di.qualifier.PracticeDuration;
import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.data.EmojiToDrawProvider;


/**
 * This is al alternative implementation of {@link PracticeContract.Presenter}
 * for the <code>sequential</code> that is just meant to be used in the testing/development phase.
 * No point to get fancy with real decorators and stuff, plain old inheritance is OK.
 */
@ActivityScope
public class SequentialPracticePresenter extends PracticePresenter {

    private SequentialSharedPrefsHelper sharedPrefsHelper;


    @Inject
    public SequentialPracticePresenter(EmojiDetectionProvider detectionProvider,
                                       EmojiToDrawProvider emojiToDrawProvider,
                                       @PracticeDuration Integer duration,
                                       SequentialSharedPrefsHelper sharedPrefsHelper) {
        super(detectionProvider, emojiToDrawProvider, duration);
        this.sharedPrefsHelper = sharedPrefsHelper;
    }

    @Override
    protected boolean handleNextEmoji() {
        sharedPrefsHelper.increaseCurrentIndex();
        return super.handleNextEmoji();
    }


}
