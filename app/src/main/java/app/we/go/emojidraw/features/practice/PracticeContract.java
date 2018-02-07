package app.we.go.emojidraw.features.practice;

import java.util.List;

import app.we.go.emojidraw.model.Stroke;
import app.we.go.emojidraw.arch.mvp.MVP;

public interface PracticeContract {

    interface View extends MVP.View {

        void onGuessesReturned(List<String> strings);

        void setEmojiToDraw(String emoji, String emojiDescription);

        void onEmojiDrawnCorrectly(String emoji);

        void onAllEmojisDrawn();

        void onAllEmojisDrawnWithCheat();

        void onTimeOut();

        void showTooltip(int position);

        void hideTooltip();

        void showErrorMessage();
    }

    interface Presenter extends MVP.Presenter<View> {

        void onStrokeAdded(List<Stroke> strokes);

        void onTimeExpired();

        void onSkip();

    }

}
