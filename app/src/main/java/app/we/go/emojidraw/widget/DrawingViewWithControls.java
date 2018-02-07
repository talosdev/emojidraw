package app.we.go.emojidraw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.we.go.emojidraw.R;
import app.we.go.emojidraw.model.Stroke;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Wraps the {@link DrawingView} and adds Undo, Clear and Skip buttons.
 */
@SuppressWarnings("WeakerAccess")
public class DrawingViewWithControls extends RelativeLayout {

    @BindView(R.id.drawing_area)
    DrawingView drawingView;

    @BindView(R.id.win_emoji)
    TextView winEmojiTextView;

    @BindView(R.id.win_overlay)
    View winOverlayView;


    private final PublishSubject<Object> skipPubSub = PublishSubject.create();

    // An irrelevant object that is emitted by the skipObservable
    @SuppressWarnings("FieldCanBeLocal")
    private Object irrelevant = new Object();


    public DrawingViewWithControls(final Context context) {
        super(context);
        init(context);
    }

    public DrawingViewWithControls(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawingViewWithControls(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        inflate(context, R.layout.drawing_view_with_controls, this);
        ButterKnife.bind(this);
    }

    /**
     * @see DrawingView#getStrokesObservable()
     * @return
     */
    public Observable<List<Stroke>> getStrokesObservable() {
        return drawingView.getStrokesObservable();
    }

    /**
     * An observable that emits when the skip button is clicked
     * @return
     */
    public Observable<Object> getSkipObservable() {
        return skipPubSub;
    }


    @OnClick(R.id.undo_button)
    public void onUndo() {
        drawingView.undo();
    }

    @OnClick(R.id.clear_button)
    public void onClear() {
        drawingView.clear();
    }

    @OnClick(R.id.skip_button)
    public void onSkip() {
        drawingView.clear();
        irrelevant = new Object();
        skipPubSub.onNext(irrelevant);
    }


    public void animateWin(String winEmoji) {
        float scale = 1.75f;
        int duration = 500;
        winEmojiTextView.setAlpha(0.5f);
        winEmojiTextView.setText(winEmoji);
        winOverlayView.setVisibility(VISIBLE);
        winEmojiTextView.animate()
                .alpha(1f)
                .scaleX(scale)
                .scaleY(scale)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(duration)
                .withEndAction(() -> {
                    winOverlayView.setVisibility(GONE);
                    winEmojiTextView.setAlpha(0f);
                })
                .start();
    }
}
