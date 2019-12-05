package app.we.go.emojidraw.data

import app.we.go.emojidraw.model.Emoji
import java.util.*

private const val MAX_TRIES = 1000

/**
 * Provides a randomly selected list of emojis, that match the requested android version of the user's
 * device (`sdkVersion`)
 */
class RandomEmojiToDrawProvider(private val emojiList: List<Emoji>,
                                private val sdkVersion: Int): EmojiToDrawProvider {
    private val random: Random = Random()

    override fun provide(n: Int): List<EmojiToDraw> {

        return (1..MAX_TRIES).asSequence().map {
            emojiList[random.nextInt(emojiList.size)]
        }
                .filter {
                    it.sinceVersion <= sdkVersion
                }
                .distinct()
                .take(n)
                .map {
                    EmojiToDraw(it.description, it.emoji)
                }.toList()

    }

}