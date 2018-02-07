package app.we.go.emojidraw.features.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import app.we.go.emojidraw.R;
import app.we.go.emojidraw.features.practice.PracticeActivity;
import app.we.go.emojidraw.widget.CenteredToast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.emoji_icon)
    View emojiIcon;

    @BindView(R.id.draw_icon)
    View drawIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_practice_button)
    public void onPracticeClick() {
        startActivity(PracticeActivity.newIntent(this));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    @OnClick(R.id.challenge_friend)
    public void onChallengeClick() {
        CenteredToast.makeText(this, R.string.feature_not_available, Toast.LENGTH_SHORT).show();
    }
}
