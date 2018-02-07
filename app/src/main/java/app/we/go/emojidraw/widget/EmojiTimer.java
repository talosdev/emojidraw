package app.we.go.emojidraw.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import app.we.go.emojidraw.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;

/**
 * Countdown timer that displays the time using emojis and starts automatically when it is
 * attached to the window. The <code>timeLimit</code> is configurable. It exposes a completable
 * so that clients of this view can react to the expiration.
 */
@SuppressWarnings("WeakerAccess")
public class EmojiTimer extends LinearLayout {

    @BindView(R.id.timer_text)
    TextView timerText;

    private int timeLimit = -1;

    final CompositeDisposable disposables = new CompositeDisposable();


    private final String[] emojiNumbers = new String[]{
            "0⃣", "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "6⃣", "7⃣", "8⃣", "9⃣"};

    /**
     * This is used both for updating the view and for the expire completable that we expose.
     * The best reference material for the rx-magic that we are using:
     * https://blog.thoughtram.io/angular/2016/06/16/cold-vs-hot-observables.html
     */
    private ConnectableObservable<Long> countdownObservable;


    public EmojiTimer(final Context context) {
        super(context);
        init(context);
    }

    public EmojiTimer(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public EmojiTimer(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        inflate(context, R.layout.emoji_timer, this);
        if (!isInEditMode()) {
            ButterKnife.bind(this);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!isInEditMode()) {
            if (timeLimit < 0) {
                throw new IllegalStateException("The timeLimit must be set before the view is" +
                        " attached to the window");
            }

            disposables.add(Observable.just((long) timeLimit)
                    .mergeWith(
                            countdownObservable
                                    .map(v -> timeLimit - v - 1))
                    .map(this::convertToTimeString)
                    .map(this::convertToEmoji)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            s -> timerText.setText(s),
                            this::onError)
            );

            countdownObservable.connect();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposables.clear();
    }

    private void onError(final Throwable throwable) {
        Log.e("EmojiTimer", "Fatal error in timer", throwable);
        // This should never really happen - crash the app so that we
        throw new RuntimeException(throwable);
    }

    /**
     * A Completable that completes when the timer expires
     * @return
     */
    public Completable getExpirationCompletable() {
        return Completable
                .fromObservable(countdownObservable)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Set the time limit of the timer
     * @param timeLimit
     */
    public void setTimeLimit(final int timeLimit) {
        this.timeLimit = timeLimit;
        countdownObservable =
                Observable.interval(1, TimeUnit.SECONDS) // by default operates on computation scheduler
                        .take(timeLimit)
                        .publish();
    }

    @SuppressLint("DefaultLocale") // locale is irrelevant for the formatting we are using
    private String convertToTimeString(final long nsec) {
        Log.d("s", "Converting " + nsec);
        if (nsec > 600) {
            throw new IllegalArgumentException("Timer currently does not support values greater than 10 min");
        }
        int nmin = (int) (nsec / 60);
        int remSec = (int) (nsec % 60);
        return String.format("%d:%02d", nmin, remSec);

    }

    private String convertToEmoji(final String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ':') {
                sb.append(c);
            } else {
                int e = Character.getNumericValue(c);
                sb.append(emojiNumbers[e]);
            }
        }
        return sb.toString();
    }

    public void stop() {
        disposables.clear();
    }
}
