package app.we.go.emojidraw.features.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import app.we.go.emojidraw.R
import app.we.go.emojidraw.features.practice.PracticeActivity
import app.we.go.emojidraw.widget.CenteredToast
import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.start_practice_button)
    fun onPracticeClick() {
        startActivity(PracticeActivity.newIntent(this))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    @OnClick(R.id.challenge_friend)
    fun onChallengeClick() {
        CenteredToast.makeText(this, R.string.feature_not_available, Toast.LENGTH_SHORT).show()
    }
}
