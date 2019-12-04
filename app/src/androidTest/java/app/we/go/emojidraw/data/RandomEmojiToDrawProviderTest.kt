package app.we.go.emojidraw.data

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.we.go.emojidraw.model.Emoji
import app.we.go.emojidraw.util.EmojiFileReader
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


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
        verify(emojisToDraw, 22, 100)
    }


    @Test
    fun testSDK26() {
        provider = RandomEmojiToDrawProvider(emojiList, 26)
        val emojisToDraw = provider.provide(50)
        verify(emojisToDraw, 26, 50)
    }


    @Test
    fun test2Emojis() {
        provider = RandomEmojiToDrawProvider(emojiList, 19)
        val emojisToDraw = provider.provide(2)
        verify(emojisToDraw, 19, 2)
    }


    @Test
    fun testRunOutOfEmojis() {
        provider = RandomEmojiToDrawProvider(emojiList.subList(0, 20), 22)
        val emojisToDraw = provider.provide(50)
        // Should not go into infinite loop, but will contain less than 50 elements
        assertTrue(emojisToDraw.size < 50)
        verifyVersion(emojisToDraw, 20)
    }

    private fun verify(emojisToDraw: List<EmojiToDraw>, sdkVersion: Int, expectedSize: Int) {
        verifySize(emojisToDraw, expectedSize)
        verifyVersion(emojisToDraw, sdkVersion)
        verifyUnique(emojisToDraw)
    }

    private fun verifySize(emojisToDraw: List<EmojiToDraw>, expectedSize: Int) {
        assertEquals(expectedSize, emojisToDraw.size)
    }

    private fun verifyVersion(emojisToDraw: List<EmojiToDraw>, sdkVersion: Int) {
        assertTrue(emojisToDraw.all {
            sdkVersion >= getVersionOfEmoji(it.emoji)
        })
    }

    private fun verifyUnique(emojisToDraw: List<EmojiToDraw>) {
        assertEquals(emojisToDraw.size, emojisToDraw.distinct().size)
    }

    // Not s0 efficient way to get version of emoji, but doesn't matter
    private fun getVersionOfEmoji(emoji: String): Int {
        return emojiList.find {
            it.emoji == emoji
        }?.sinceVersion ?: Int.MAX_VALUE
    }


}

