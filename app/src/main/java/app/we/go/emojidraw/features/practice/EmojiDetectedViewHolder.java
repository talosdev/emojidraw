package app.we.go.emojidraw.features.practice;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class EmojiDetectedViewHolder extends RecyclerView.ViewHolder {


    private final TextView emojiView;
    private final ObjectAnimator pulseAnimation;

    EmojiDetectedViewHolder(View itemView) {
        super(itemView);
        emojiView = (TextView) itemView;

        pulseAnimation = ObjectAnimator.ofPropertyValuesHolder(
                emojiView,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        pulseAnimation.setDuration(310);

        pulseAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        pulseAnimation.setRepeatMode(ObjectAnimator.REVERSE);
    }


    void bind(String emoji, boolean isTheOne) {
        emojiView.setText(emoji);

        // show a pulse animation on the element that represents the correct emoji
        if (isTheOne) {
            pulseAnimation.start();
        } else {
            pulseAnimation.cancel();
        }
    }
}
