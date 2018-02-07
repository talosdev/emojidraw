package app.we.go.emojidraw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.we.go.emojidraw.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CutePopup extends FrameLayout {

    @BindView(R.id.popup_container)
    View containerView;

    @BindView(R.id.first_line)
    TextView firstLineTextView;

    @BindView(R.id.second_line)
    TextView secondLineTextView;

    @BindView(R.id.emoji_emotion)
    TextView emojiTextView;


    public CutePopup(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CutePopup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CutePopup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public CutePopup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View v = LayoutInflater.from(context).inflate(R.layout.popup_generic, this, true);

        ButterKnife.bind(this, v);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CutePopup,
                0, 0);
        try {
            Drawable backgroundDrawable = a.getDrawable(R.styleable.CutePopup_cp_backgroundDrawable);
            String firstLine = a.getString(R.styleable.CutePopup_cp_firstLine);
            String secondLine = a.getString(R.styleable.CutePopup_cp_secondLine);
            String emoji = a.getString(R.styleable.CutePopup_cp_emoji);

            if (backgroundDrawable!=null) {
                containerView.setBackground(backgroundDrawable);
            }

            if (!TextUtils.isEmpty(firstLine)) {
                firstLineTextView.setText(firstLine);
            }
            if (!TextUtils.isEmpty(secondLine)) {
                secondLineTextView.setVisibility(VISIBLE);
                secondLineTextView.setText(secondLine);
            }
            if (!TextUtils.isEmpty(emoji)) {
                emojiTextView.setText(emoji);
            }

        } finally {
            a.recycle();
        }
    }

}
