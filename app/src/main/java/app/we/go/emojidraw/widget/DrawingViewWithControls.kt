package app.we.go.emojidraw.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout
import app.we.go.emojidraw.R
import app.we.go.emojidraw.model.Stroke
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.drawing_view_with_controls.view.*

/**
 * Wraps the [DrawingView] and adds Undo, Clear and Skip buttons.
 */
class DrawingViewWithControls @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val skipPubSub = PublishSubject.create<Any>()

    init {
        View.inflate(context, R.layout.drawing_view_with_controls, this)

        undoButton.setOnClickListener {
            drawingArea.undo()
        }

        clearButton.setOnClickListener { drawingArea.clear() }

        skipButton.setOnClickListener {
            drawingArea.clear()
            skipPubSub.onNext(Any())
        }
    }


    /**
     * @see DrawingView.getStrokesObservable
     * @return
     */
    val strokesObservable: Observable<List<Stroke>>
        get() = drawingArea.strokesObservable

    /**
     * An observable that emits when the skip button is clicked
     * @return
     */
    val skipObservable: Observable<Any>
        get() = skipPubSub


    fun clear() {
        drawingArea.clear()
    }


    fun animateWin(winEmoji: String) {
        val scale = 1.75f
        val duration = 500
        winOverlay.visibility = View.VISIBLE
        winEmojiTextView.apply {
            alpha = 0.5f
            text = winEmoji
            animate()
                .alpha(1f)
                .scaleX(scale)
                .scaleY(scale)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(duration.toLong())
                .withEndAction {
                    this@DrawingViewWithControls.winOverlay.visibility = View.GONE
                    winEmojiTextView.alpha = 0f
                }
                .start()
        }
    }
}
