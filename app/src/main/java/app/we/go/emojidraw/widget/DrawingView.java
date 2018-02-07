package app.we.go.emojidraw.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import app.we.go.emojidraw.R;
import app.we.go.emojidraw.model.Stroke;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * View that allows the user to draw into it. Offers some configuration options:
 * background color, paint color and stroke width (as well as, obviously, size).
 * Allows for undo and clear option. Exposes an observable with the strokes currently drawn.
 */
public class DrawingView extends View {

    private Paint paint;
    private Path currentPath;
    private ArrayList<Path> paths;
    private Context context;
    private Canvas drawingCanvas;
    private Bitmap drawingBitmap;

    private List<Stroke> strokes;
    private Stroke currentStroke;

    private final PublishSubject<List<Stroke>> pubsub = PublishSubject.create();
    private @ColorInt int backgroundColor;
    private @ColorInt int paintColor;
    private float strokeWidth;

    public DrawingView(Context c) {
        super(c);
        init(context, null);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawingBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        drawingCanvas = new Canvas(drawingBitmap);
        clearDrawingCanvas();
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context=context;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DrawingView,
                0, 0);

        try {
            backgroundColor = a.getColor(R.styleable.DrawingView_dv_backgroundColor,
                    ContextCompat.getColor(context, android.R.color.white));
            paintColor = a.getColor(R.styleable.DrawingView_dv_paintColor,
                    ContextCompat.getColor(context, android.R.color.black));
            strokeWidth = a.getFloat(R.styleable.DrawingView_dv_strokeWidth, 10f);
        } finally {
            a.recycle();
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(paintColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);

        currentPath = new Path();
        paths = new ArrayList<>();

        currentStroke = new Stroke();
        strokes = new ArrayList<>();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < paths.size(); i++) {
            drawingCanvas.drawPath(paths.get(i), paint);
        }

        drawingCanvas.drawPath(currentPath, paint);

        canvas.drawBitmap(drawingBitmap, 0, 0, paint);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                // Little hack to make sure "points" can be drawn
                currentPath.moveTo(event.getX()-1, event.getY()-1);
                currentPath.lineTo(event.getX()+1, event.getY()+1);
                currentStroke.addPoint((int) event.getX(), (int) event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(event.getX(), event.getY());
                currentStroke.addPoint((int) event.getX(), (int) event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                paths.add(currentPath);
                strokes.add(currentStroke);
                currentPath = new Path();
                currentStroke = new Stroke();
                invalidate(); // need this here as well for "points" that are up and down events (without passing from move)
                onStrokeAdded();
                break;
        }

        return true;
    }

    private void onStrokeAdded() {
       pubsub.onNext(strokes);
    }

    /**
     * An Observable that emits, on each stroke (or undo/clear actions) the list strokes that are
     * currently drawn on the canvas.
     * @return
     */
    public Observable<List<Stroke>> getStrokesObservable() {
        return pubsub;
    }

    /**
     * Clear the view completely
     */
    public void clear() {
        paths.clear();
        strokes.clear();
        pubsub.onNext(strokes);
        clearDrawingCanvas();
        invalidate();
    }

    /**
     * Undo the last stroke that was drawn.
     */
    public void undo() {
        if (!paths.isEmpty()) {
            clearDrawingCanvas();
            paths.remove(paths.size() - 1);
            strokes.remove(strokes.size() - 1);
            pubsub.onNext(strokes);
            invalidate();
        }
    }

    private void clearDrawingCanvas() {
        drawingCanvas.drawColor(backgroundColor);
    }


}