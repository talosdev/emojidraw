package app.we.go.emojidraw.data

import app.we.go.emojidraw.features.practice.SequentialSharedPrefsHelper
import app.we.go.emojidraw.model.Emoji

class SequentialEmojiToDrawProvider(private val sequentialSharedPrefsHelper: SequentialSharedPrefsHelper,
                                    private val emojiList: List<Emoji>,
                                    private val sdkVersion: Int) : EmojiToDrawProvider {

    override fun provide(n: Int): List<EmojiToDraw> {

        val startingIndex = sequentialSharedPrefsHelper.currentIndex

        return emojiList.slice(startingIndex..(emojiList.size-1))
            .asSequence()
            .filter { it.sinceVersion <= sdkVersion }
            .take(n)
            .map { EmojiToDraw(it.description, it.emoji)}
            .toList()

    }
}
