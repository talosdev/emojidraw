package app.we.go.emojidraw.features.practice

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.we.go.emojidraw.R

class EmojiDetectedAdapter(val context: Context) : RecyclerView.Adapter<EmojiDetectedViewHolder>() {

    lateinit var emojiToDraw: String

    var detectedList: MutableList<String> = ArrayList()
        set(list) {
            val diffResult = DiffUtil.calculateDiff(EmojiDiffCallback(list, detectedList))
            diffResult.dispatchUpdatesTo(this)
            field.clear()
            field.addAll(list)
        }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EmojiDetectedViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.emoji_detected_item, parent, false)
        return EmojiDetectedViewHolder(itemView)
    }

    override fun getItemCount() = detectedList.size

    override fun onBindViewHolder(holder: EmojiDetectedViewHolder, position: Int) {
        val detectedEmoji = detectedList[position]
        holder.bind(detectedEmoji, detectedEmoji == emojiToDraw)
    }


}