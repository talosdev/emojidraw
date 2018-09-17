package app.we.go.emojidraw.data

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import app.we.go.emojidraw.R.drawable.emoji
import app.we.go.emojidraw.model.Emoji
import app.we.go.emojidraw.util.EmojiFileReader
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class RandomEmojiToDrawProviderTest {


    private lateinit var emojiList: List<Emoji>

    private lateinit var provider: RandomEmojiToDrawProvider

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        val reader = EmojiFileReader(context)
        emojiList = reader.emojiList
    }


    @Test
    fun testSimple() {
        provider = RandomEmojiToDrawProvider(emojiList, 22)
        val emojisToDraw = provider.provide(100)
        verify(emojisToDraw, 22)
    }


    @Test
    fun testSDK26() {
        provider = RandomEmojiToDrawProvider(emojiList, 26)
        val emojisToDraw = provider.provide(50)
        verify(emojisToDraw, 26)
    }


    @Test
    fun test2Emojis() {
        provider = RandomEmojiToDrawProvider(emojiList, 19)
        val emojisToDraw = provider.provide(2)
        verify(emojisToDraw, 19)
    }


    @Test
    fun testRunOutOfEmojis() {
        provider = RandomEmojiToDrawProvider(emojiList.subList(0, 20), 22)
        val emojisToDraw = provider.provide(50)
        // Should not go into infinite loop, but will not contain
        verifyVersion(emojisToDraw, 20)
    }

    private fun verify(emojisToDraw: List<EmojiToDraw>, sdkVersion: Int) {
        verifyVersion(emojisToDraw, sdkVersion)
        verifyUnique(emojisToDraw)
    }

    private fun verifyVersion(emojisToDraw: List<EmojiToDraw>, sdkVersion: Int) {
        assertTrue(emojisToDraw.all {
            sdkVersion >= getVersionOfEmoji(it.emoji)
        })
    }

    private fun verifyUnique(emojisToDraw: List<EmojiToDraw>) {
        assertEquals(emojisToDraw.size,
            emojisToDraw.map { it.emoji }
            .distinct().size)

    }

    private fun getVersionOfEmoji(emoji: String): Int {
        return emojiList.find {
            it.emoji == emoji
        }?.sinceVersion ?: Int.MAX_VALUE

    }


}

