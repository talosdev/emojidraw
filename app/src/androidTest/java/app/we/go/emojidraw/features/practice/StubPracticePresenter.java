package app.we.go.emojidraw.features.practice;

import java.util.List;

import javax.inject.Inject;

import app.we.go.emojidraw.arch.mvp.BasePresenter;
import app.we.go.emojidraw.model.Stroke;

/**
 * Stub implementation of {@link PracticeContract.Presenter} that:
 * 1) notifies the view that all emojis guessed correctly on the 1st stroke
 * 2) if user has cheated (skipped), notifies the view accordingly
 * 2) calls the expired callback when it receives the signal that time expired.
 */
public class StubPracticePresenter extends BasePresenter<PracticeContract.View>
        implements PracticeContract.Presenter {


    private boolean cheated;

    @Inject
    public StubPracticePresenter() {

    }


    @Override
    public void onStrokeAdded(List<Stroke> strokes) {
        if (view != null && !strokes.isEmpty()) {
            if (cheated) {
                view.onAllEmojisDrawnWithCheat();
            } else {
                view.onAllEmojisDrawn();
            }
        }
    }

    @Override
    public void onTimeExpired() {
        if (view != null) {
            view.onTimeOut();
        }
    }

    @Override
    public void onSkip() {
        cheated = true;
    }
}
