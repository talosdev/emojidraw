package app.we.go.emojidraw.features.practice

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import app.we.go.emojidraw.R
import app.we.go.emojidraw.ThisApplication
import app.we.go.emojidraw.arch.di.PracticeDuration
import app.we.go.emojidraw.widget.CenteredToast
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.practice_activity.*
import javax.inject.Inject

class PracticeActivity : AppCompatActivity(), PracticeContract.View {


    @Inject
    lateinit var presenter: PracticeContract.Presenter

    @JvmField
    @field:[Inject PracticeDuration]
    var duration: Int = 0

    private lateinit var adapter: EmojiDetectedAdapter

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_activity)

        ThisApplication.getComponent(this)
            .plusPracticeComponent()
            .inject(this)

        emojiDetectedRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = EmojiDetectedAdapter(this)
        emojiDetectedRecycler.adapter = adapter

        presenter.bindView(this)

        emojiTimer.setTimeLimit(duration)

        disposables.add(emojiTimer
            .expirationCompletable
            .subscribe { presenter.onTimeExpired() })


        disposables.add(drawingView.strokesObservable
            .subscribe { presenter.onStrokeAdded(it) })

        disposables.add(drawingView.skipObservable
            .subscribe { presenter.onSkip() })

    }

    override fun onGuessesReturned(emojis: List<String>) {
        emojiDetectedContainer!!.visibility = View.VISIBLE
        adapter.detectedList = emojis
    }


    override fun setEmojiToDraw(emoji: String, emojiDescription: String) {
        adapter.emojiToDraw = emoji
        whatToDrawTextView!!.text = emojiDescription
    }

    override fun onEmojiDrawnCorrectly(emoji: String) {
        emojiDetectedContainer.visibility = View.INVISIBLE
        adapter.detectedList = emptyList()
        drawingView.animateWin(emoji)
        drawingView.clear()
    }

    override fun onAllEmojisDrawn() {
        stopTimer()
        showPopup(R.layout.popup_win)
    }

    override fun onAllEmojisDrawnWithCheat() {
        stopTimer()
        showPopup(R.layout.popup_cheated)
    }

    private fun stopTimer() {
        emojiTimer.stop()
        disposables.dispose()
    }

    override fun showTooltip(position: Int) {
        // We need to "post" this, to make sure that the recycler has laid out and measured its children
        Handler().post {
            tooltipContainer.visibility = View.VISIBLE

            val n = adapter.itemCount
            // The tooltip indicator is centered horizontally, we need to translate it accordingly
            // so that it points to the correct recycler item.
            // CAUTION: this calculation relies on the exact layout of the recycler, eg it will be off
            // if the recycler has item decorations
            val targetRecyclerItem = emojiDetectedRecycler?.layoutManager?.findViewByPosition(position)
            targetRecyclerItem?.let {
                val lp = it.layoutParams as ViewGroup.MarginLayoutParams
                val width = it.measuredWidth + lp.leftMargin + lp.rightMargin
                tooltipIndicator.translationX = (position - (n - 1) / 2f) * width
            }
        }
    }

    override fun hideTooltip() {
        tooltipContainer.visibility = View.INVISIBLE
    }

    override fun showErrorMessage() {
        CenteredToast.show(this, R.string.network_error, Toast.LENGTH_SHORT)
    }

    override fun onTimeOut() {
        showPopup(R.layout.popup_timeout)
    }

    private fun showPopup(@LayoutRes layout: Int) {
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(layout, null)
        val alert = AlertDialog.Builder(this).run {
            setView(dialogView)
            setCancelable(false)
            setOnDismissListener { this@PracticeActivity.finish() }
            create()
        }

        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.setOnClickListener { alert.dismiss() }

        alert.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overrideExitTransition()
    }

    override fun finish() {
        super.finish()
        overrideExitTransition()
    }

    private fun overrideExitTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, PracticeActivity::class.java)
        }
    }
}
