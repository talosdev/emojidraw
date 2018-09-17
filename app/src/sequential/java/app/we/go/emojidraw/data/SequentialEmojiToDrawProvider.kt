package app.we.go.emojidraw.data

import app.we.go.emojidraw.features.practice.SequentialSharedPrefsHelper
import app.we.go.emojidraw.model.Emoji

// TODO - add this kotlin goodness to the README
class SequentialEmojiToDrawProvider(private val sequentialSharedPrefsHelper: SequentialSharedPrefsHelper, private val emojiList: List<Emoji>, private val sdkVersion: Int) : EmojiToDrawProvider {

    override fun provide(n: Int): List<EmojiToDraw> {

        val startingIndex = sequentialSharedPrefsHelper.currentIndex

        return emojiList.slice(startingIndex..(emojiList.size-1))
            .asSequence()
            .takeWhile { it.sinceVersion <= sdkVersion }
            .map { EmojiToDraw(it.description, it.emoji)}
            .take(n)
            .toList()

    }
}
