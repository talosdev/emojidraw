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
        val list = ArrayList<EmojiToDraw>()
        for (i in 0 until n) {
            list.add(getEmojiChallengeNotInList(list))
        }
        return list
    }


    private fun getEmojiChallengeNotInList(list: List<EmojiToDraw>): EmojiToDraw {
        for (i in 1..MAX_TRIES) {  // avoid going into infinite loop
            val emojiToDraw = provideOne()
            if (!list.contains(emojiToDraw)) return emojiToDraw
        }

        return provideOne() //we have exhausted MAX_TRIES, just get some emoji and return it, will probably be a duplicate...
    }

    private fun provideOne(): EmojiToDraw {
        var emoji : Emoji
        do {
            emoji = emojiList[random.nextInt(emojiList.size)]
        } while (!isVersionSatisfied(emoji))
        return EmojiToDraw(emoji.description, emoji.emoji)
    }

    private fun isVersionSatisfied(emoji: Emoji) = emoji.sinceVersion <= sdkVersion


}