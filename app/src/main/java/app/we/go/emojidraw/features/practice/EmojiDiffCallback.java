package app.we.go.emojidraw.features.practice;

import android.support.v7.util.DiffUtil;

import java.util.List;

class EmojiDiffCallback extends DiffUtil.Callback{

    private final List<String> oldEmojis;
    private final List<String> newEmojis;

    EmojiDiffCallback(List<String> newEmojis, List<String> oldEmojis) {
        this.newEmojis = newEmojis;
        this.oldEmojis = oldEmojis;
    }

    @Override
    public int getOldListSize() {
        return oldEmojis.size();
    }

    @Override
    public int getNewListSize() {
        return newEmojis.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEmojis.get(oldItemPosition).equals(newEmojis.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldEmojis.get(oldItemPosition).equals(newEmojis.get(newItemPosition));
    }

}