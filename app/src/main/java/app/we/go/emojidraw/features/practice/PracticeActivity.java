package app.we.go.emojidraw.features.practice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import app.we.go.emojidraw.R;
import app.we.go.emojidraw.ThisApplication;
import app.we.go.emojidraw.arch.di.PracticeDuration;
import app.we.go.emojidraw.widget.CenteredToast;
import app.we.go.emojidraw.widget.DrawingViewWithControls;
import app.we.go.emojidraw.widget.EmojiTimer;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

@SuppressWarnings("WeakerAccess")
public class PracticeActivity extends AppCompatActivity implements PracticeContract.View {

    @BindView(R.id.emoji_detected_container)
    ViewGroup emojiDetectedContainer;

    @BindView(R.id.emoji_detected_recycler)
    RecyclerView emojiDetectedRecycler;

    @BindView(R.id.what_to_draw)
    TextView whatToDrawTextView;

    @BindView(R.id.drawing_view)
    DrawingViewWithControls drawingViewWithControls;

    @BindView(R.id.emojiTimer)
    EmojiTimer timer;

    @BindView(R.id.tooltip_container)
    ViewGroup tooltipContainer;

    @BindView(R.id.tooltip_indicator)
    View tooltipIndicator;


    @Inject
    PracticeContract.Presenter presenter;

    @Inject
    @PracticeDuration
    Integer duration;

    private EmojiDetectedAdapter adapter;

    private final CompositeDisposable disposables = new CompositeDisposable();


    public static Intent newIntent(Context context) {
        return new Intent(context, PracticeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_activity);

        ButterKnife.bind(this);

        ThisApplication.getComponent(this)
                .plusPracticeComponent()
                .inject(this);

        emojiDetectedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new EmojiDetectedAdapter(this);
        emojiDetectedRecycler.setAdapter(adapter);

        presenter.bindView(this);

        timer.setTimeLimit(duration);

        disposables.add(timer
            .getExpirationCompletable()
            .subscribe(presenter::onTimeExpired));


        disposables.add(drawingViewWithControls.getStrokesObservable()
                .subscribe(presenter::onStrokeAdded));

        disposables.add(drawingViewWithControls.getSkipObservable()
        .subscribe(v -> presenter.onSkip()));

    }

    @Override
    public void onGuessesReturned(final List<String> emojis) {
        emojiDetectedContainer.setVisibility(View.VISIBLE);
        adapter.setDetectedList(emojis);
    }


    @Override
    public void setEmojiToDraw(String emoji, String emojiDescription) {
        adapter.setEmojiToDraw(emoji);
        whatToDrawTextView.setText(emojiDescription);
    }

    @Override
    public void onEmojiDrawnCorrectly(String emoji) {
        emojiDetectedContainer.setVisibility(View.INVISIBLE);
        adapter.setDetectedList(Collections.emptyList());
        drawingViewWithControls.animateWin(emoji);
        drawingViewWithControls.onClear();
    }

    @Override
    public void onAllEmojisDrawn() {
        stopTimer();
        showPopup(R.layout.popup_win);
    }

    @Override
    public void onAllEmojisDrawnWithCheat() {
        stopTimer();
        showPopup(R.layout.popup_cheated);
    }

    private void stopTimer() {
        timer.stop();
        disposables.dispose();
    }

    @Override
    public void showTooltip(int position) {
        // We need to "post" this, to make sure that the recycler has laid out and measured its children
        new Handler().post(() -> {
            tooltipContainer.setVisibility(View.VISIBLE);

            int n = adapter.getItemCount();
            // The tooltip indicator is centered horizontally, we need to translate it accordingly
            // so that it points to the correct recycler item.
            // CAUTION: this calculation relies on the exact layout of the recycler, eg it will be off
            // if the recycler has item decorations
            final View targetRecyclerItem = emojiDetectedRecycler.getLayoutManager().findViewByPosition(position);
            final ViewGroup.MarginLayoutParams lp =
                    (ViewGroup.MarginLayoutParams) targetRecyclerItem.getLayoutParams();
            final int width = targetRecyclerItem.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            tooltipIndicator.setTranslationX((position - (n-1)/2f) * (width) );


        });

    }

    @Override
    public void hideTooltip() {
        tooltipContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage() {
        CenteredToast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onTimeOut() {
        showPopup(R.layout.popup_timeout);
    }

    private void showPopup(@LayoutRes int layout) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setOnDismissListener(dialog -> PracticeActivity.this.finish());


        AlertDialog b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogView.setOnClickListener(v -> b.dismiss());

        b.show();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overrideExitTransition();
    }

    @Override
    public void finish() {
        super.finish();
        overrideExitTransition();
    }

    private void overrideExitTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
