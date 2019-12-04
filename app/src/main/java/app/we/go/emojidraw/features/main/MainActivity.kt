package app.we.go.emojidraw.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import app.we.go.emojidraw.R
import app.we.go.emojidraw.features.practice.PracticeActivity
import app.we.go.emojidraw.widget.CenteredToast
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        startPracticeButton.setOnClickListener {
            startActivity(PracticeActivity.newIntent(this))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        challengeFriendButton.setOnClickListener {
            CenteredToast.show(this, R.string.feature_not_available, Toast.LENGTH_SHORT)
        }
    }


}
