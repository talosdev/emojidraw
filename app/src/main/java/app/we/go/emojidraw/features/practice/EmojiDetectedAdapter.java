package app.we.go.emojidraw.features.practice;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import app.we.go.emojidraw.R;

class EmojiDetectedAdapter extends RecyclerView.Adapter<EmojiDetectedViewHolder> {


    private List<String> detectedList;
    private final Context context;
    private String emojiToDraw;

    EmojiDetectedAdapter(Context context) {
        this.context = context;
        detectedList = Collections.emptyList();
    }

    @Override
    public EmojiDetectedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.emoji_detected_item, parent, false);
        return new EmojiDetectedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmojiDetectedViewHolder holder, int position) {
        final String detectedEmoji = detectedList.get(position);
        holder.bind(detectedEmoji, detectedEmoji.equals(emojiToDraw));
    }


    void setDetectedList(List<String> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EmojiDiffCallback(list, detectedList));
        diffResult.dispatchUpdatesTo(this);
        detectedList = list;
    }

    @Override
    public int getItemCount() {
        if (detectedList == null) {
            return 0;
        }
        return detectedList.size();
    }

    void setEmojiToDraw(String emojiToDraw) {
        this.emojiToDraw = emojiToDraw;
    }
}
