package app.we.go.emojidraw.features.practice

import androidx.recyclerview.widget.DiffUtil

internal class EmojiDiffCallback(private val newEmojis: List<String>, private val oldEmojis: List<String>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldEmojis.size
    }

    override fun getNewListSize(): Int {
        return newEmojis.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldEmojis[oldItemPosition] == newEmojis[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldEmojis[oldItemPosition] == newEmojis[newItemPosition]
    }

}