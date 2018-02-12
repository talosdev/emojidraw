package app.we.go.emojidraw.features.practice;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import app.we.go.emojidraw.api.EmojiDetectionProvider;
import app.we.go.emojidraw.arch.di.qualifier.PracticeSize;
import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.arch.mvp.BasePresenter;
import app.we.go.emojidraw.data.EmojiToDraw;
import app.we.go.emojidraw.data.EmojiToDrawProvider;
import app.we.go.emojidraw.model.Stroke;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@ActivityScope
public class PracticePresenter extends BasePresenter<PracticeContract.View>
        implements PracticeContract.Presenter {

    private final EmojiDetectionProvider detectionProvider;
    private final EmojiToDrawProvider emojiToDrawProvider;
    private Integer nEmojis;

    private List<EmojiToDraw> emojisToDraw;
    private int currentEmojiIndex;
    private boolean cheated;
    private boolean won;



    @Inject
    public PracticePresenter(EmojiDetectionProvider detectionProvider, EmojiToDrawProvider emojiToDrawProvider,
                             @PracticeSize Integer nEmojis) {
        this.detectionProvider = detectionProvider;
        this.emojiToDrawProvider = emojiToDrawProvider;
        this.nEmojis = nEmojis;
    }


    @Override
    protected void init() {
        super.init();
        emojisToDraw = emojiToDrawProvider.provide(nEmojis);
        updateEmojiToDraw();
    }

    private void updateEmojiToDraw() {
        if (view != null) {
            final EmojiToDraw emojiToDraw = emojisToDraw.get(currentEmojiIndex);
            view.setEmojiToDraw(emojiToDraw.emoji(), emojiToDraw.description());
        }
    }

    @Override
    public void onStrokeAdded(final List<Stroke> strokes) {
        disposables.add(
                detectionProvider.getEmojis(strokes)
                        // TODO move to provider
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::processEmojiGuesses,
                                this::onError)
        );

    }

    private void processEmojiGuesses(List<String> emojis) {
        if (view != null) {
            view.onGuessesReturned(emojis);
            if (!emojis.isEmpty() && currentEmojiIndex < emojisToDraw.size()) {
                final int index = emojis.indexOf(emojisToDraw.get(currentEmojiIndex).emoji());
                if (index > 0) { // if 0, user has won, so no point in showing the tooltip
                    // TODO only show the tooltip once or twice
                    view.showTooltip(index);
                } else {
                    view.hideTooltip();
                }

                String currentEmoji = emojisToDraw.get(currentEmojiIndex).emoji();

                if (emojis.get(0).equals(currentEmoji)) {
                    disposables.clear(); // dispose of all pending requests
                    if (view != null) {
                        view.onEmojiDrawnCorrectly(currentEmoji);
                    }
                    handleNextEmoji();
                }
            } else {
                view.hideTooltip();
            }
        }
    }

    private void onError(Throwable throwable) {
        Log.e("Presenter", "Error", throwable);
        if (view != null) {
            view.showErrorMessage();
        }
    }


    /**
     * @return <code>true</code> if there is a next emoji, false if we're finished
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean handleNextEmoji() {
        currentEmojiIndex++;

        if (currentEmojiIndex == emojisToDraw.size()) {
            if (view != null) {
                won = true;
                if (cheated) {
                    view.onAllEmojisDrawnWithCheat();
                } else
                    view.onAllEmojisDrawn();
                }
            return false;
        } else {
            updateEmojiToDraw();
            return true;
        }
    }




    @Override
    public void onTimeExpired() {
        if (view != null && !won) {
            view.onTimeOut();
        }
    }

    @Override
    public void onSkip() {
        if (currentEmojiIndex < emojisToDraw.size() ) {
            cheated = true;
            handleNextEmoji();
        }
    }

}
