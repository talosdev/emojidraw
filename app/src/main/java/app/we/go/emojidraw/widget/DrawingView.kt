package app.we.go.emojidraw.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import app.we.go.emojidraw.R
import app.we.go.emojidraw.model.Stroke
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * View that allows the user to draw into it. Offers some configuration options:
 * background color, paint color and stroke width (as well as, obviously, size).
 * Allows for undo and clear option. Exposes an observable with the strokes currently drawn.
 */
class DrawingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint: Paint
    private var currentPath: Path = Path()
    private val paths: MutableList<Path> = mutableListOf()
    private lateinit var drawingCanvas: Canvas
    private lateinit var drawingBitmap: Bitmap

    private val strokes: MutableList<Stroke> = mutableListOf()
    private var currentStroke: Stroke = Stroke()

    private val pubsub = PublishSubject.create<List<Stroke>>()

    @ColorInt
    private val bgColor: Int
    @ColorInt
    private val paintColor: Int

    private val strokeWidth: Float


    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.DrawingView,
            0, 0)

        bgColor = typedArray.getColor(R.styleable.DrawingView_dv_backgroundColor,
            ContextCompat.getColor(context, android.R.color.white))
        paintColor = typedArray.getColor(R.styleable.DrawingView_dv_paintColor,
            ContextCompat.getColor(context, android.R.color.black))
        strokeWidth = typedArray.getFloat(R.styleable.DrawingView_dv_strokeWidth, 10f)

        typedArray.recycle()

        paint = Paint().apply {
            isAntiAlias = true
            isDither = true
            color = paintColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = this@DrawingView.strokeWidth
        }
    }


    /**
     * An Observable that emits, on each stroke (or undo/clear actions) the list strokes that are
     * currently drawn on the canvas.
     * @return
     */
    val strokesObservable: Observable<List<Stroke>>
        get() = pubsub


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        drawingCanvas = Canvas(drawingBitmap)
        clearDrawingCanvas()
    }


    override fun onDraw(canvas: Canvas) {
        (paths + currentPath).map {
            drawingCanvas.drawPath(it, paint)
        }

        canvas.drawBitmap(drawingBitmap, 0f, 0f, paint)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Little hack to make sure "points" can be drawn
                currentPath.moveTo(event.x - 1, event.y - 1)
                currentPath.lineTo(event.x + 1, event.y + 1)
                currentStroke.addPoint(event.x.toInt(), event.y.toInt())
            }

            MotionEvent.ACTION_MOVE -> {
                currentPath.lineTo(event.x, event.y)
                currentStroke.addPoint(event.x.toInt(), event.y.toInt())
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                paths.add(currentPath)
                strokes.add(currentStroke)
                currentPath = Path()
                currentStroke = Stroke()
                invalidate() // need this here as well for "points" that are up and down events (without passing from move)
                onStrokeAdded()
            }
        }

        return true
    }

    private fun onStrokeAdded() {
        pubsub.onNext(strokes)
    }

    /**
     * Clear the view completely
     */
    fun clear() {
        paths.clear()
        strokes.clear()
        pubsub.onNext(strokes)
        clearDrawingCanvas()
        invalidate()
    }

    /**
     * Undo the last stroke that was drawn.
     */
    fun undo() {
        if (!paths.isEmpty()) {
            clearDrawingCanvas()
            paths.removeAt(paths.size - 1)
            strokes.removeAt(strokes.size - 1)
            pubsub.onNext(strokes)
            invalidate()
        }
    }

    private fun clearDrawingCanvas() {
        drawingCanvas.drawColor(bgColor)
    }

}