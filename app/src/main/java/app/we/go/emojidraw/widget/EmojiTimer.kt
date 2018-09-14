package app.we.go.emojidraw.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import app.we.go.emojidraw.R
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import kotlinx.android.synthetic.main.emoji_timer.view.*
import java.util.concurrent.TimeUnit

/**
 * Countdown timer that displays the time using emojis and starts automatically when it is
 * attached to the window. The `timeLimit` is configurable. It exposes a completable
 * so that clients of this view can react to the expiration.
 */
class EmojiTimer  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var timeLimit = -1

    private val disposables = CompositeDisposable()


    private val emojiNumbers = arrayOf("0⃣", "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "6⃣", "7⃣", "8⃣", "9⃣")

    /**
     * This is used both for updating the view and for the expire completable that we expose.
     * The best reference material for the rx-magic that we are using:
     * https://blog.thoughtram.io/angular/2016/06/16/cold-vs-hot-observables.html
     */
    private var countdownObservable: ConnectableObservable<Long>? = null

    /**
     * A Completable that completes when the timer expires
     * @return
     */
    val expirationCompletable: Completable
        get() = Completable
            .fromObservable(countdownObservable!!)
            .observeOn(AndroidSchedulers.mainThread())




    init {
        View.inflate(context, R.layout.emoji_timer, this)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (!isInEditMode) {
            if (timeLimit < 0) {
                throw IllegalStateException("The timeLimit must be set before the view is" + " attached to the window")
            }

            disposables.add(Observable.just(timeLimit.toLong())
                .mergeWith(
                    countdownObservable!!
                        .map { v -> timeLimit.toLong() - v - 1 })
                .map<String> { this.convertToTimeString(it) }
                .map<String> { this.convertToEmoji(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { s -> timerText.text = s },
                    { this.onError(it) })
            )

            countdownObservable!!.connect()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.clear()
    }

    private fun onError(throwable: Throwable) {
        Log.e("EmojiTimer", "Fatal error in timer", throwable)
        // This should never really happen - crash the app so that we
        throw RuntimeException(throwable)
    }

    /**
     * Set the time limit of the timer
     * @param timeLimit
     */
    fun setTimeLimit(timeLimit: Int) {
        this.timeLimit = timeLimit
        countdownObservable = Observable.interval(1, TimeUnit.SECONDS) // by default operates on computation scheduler
            .take(timeLimit.toLong())
            .publish()
    }

    @SuppressLint("DefaultLocale") // locale is irrelevant for the formatting we are using
    private fun convertToTimeString(nsec: Long): String {
        Log.d("s", "Converting $nsec")
        if (nsec > 600) {
            throw IllegalArgumentException("Timer currently does not support values greater than 10 min")
        }
        val nmin = (nsec / 60).toInt()
        val remSec = (nsec % 60).toInt()
        return String.format("%d:%02d", nmin, remSec)

    }

    private fun convertToEmoji(s: String): String {
        val joinToString = s.toCharArray().map {
           if (it == ':') {
                it.toString()
            } else {
                emojiNumbers[Character.getNumericValue(it)]
            }
        }

        return joinToString.joinToString(separator = "")
    }

    fun stop() {
        disposables.clear()
    }
}
