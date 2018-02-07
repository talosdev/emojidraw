package app.we.go.emojidraw.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.we.go.emojidraw.model.Emoji;
import app.we.go.emojidraw.util.EmojiFileReader;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(AndroidJUnit4.class)
public class RandomEmojiToDrawProviderTest {


    private List<Emoji> emojiList;

    private RandomEmojiToDrawProvider provider;

    @Before
    public void setUp() throws Exception {
        final Context context = InstrumentationRegistry.getTargetContext();
        EmojiFileReader reader = new EmojiFileReader(context);
        emojiList = reader.getList();
    }


    @Test
    public void testSimple() {
        provider = new RandomEmojiToDrawProvider(emojiList, 22);
        final List<EmojiToDraw> emojisToDraw = provider.provide(100);
        verify(emojisToDraw, 22);
    }


    @Test
    public void testSDK26() {
        provider = new RandomEmojiToDrawProvider(emojiList, 26);
        final List<EmojiToDraw> emojisToDraw = provider.provide(50);
        verify(emojisToDraw, 26);
    }


    @Test
    public void test2Emojis() {
        provider = new RandomEmojiToDrawProvider(emojiList, 19);
        final List<EmojiToDraw> emojisToDraw = provider.provide(2);
        verify(emojisToDraw, 19);
    }


    @Test
    public void testRunOutOfEmojis() {
        provider = new RandomEmojiToDrawProvider(emojiList.subList(0, 20), 22);
        final List<EmojiToDraw> emojisToDraw = provider.provide(50);
        // Should not go into infinite loop, but will not contain
        verifyVersion(emojisToDraw, 20);
    }

    private void verify(List<EmojiToDraw> emojisToDraw, int sdkVersion) {
        verifyVersion(emojisToDraw, sdkVersion);
        verifyUnique(emojisToDraw);
    }

    private void verifyVersion(List<EmojiToDraw> emojisToDraw, int sdkVersion) {
        Set<String> emojis = new HashSet<>();
        for (EmojiToDraw emojiToDraw : emojisToDraw) {
            final int sinceVersionOfEmoji = getVersionOfEmoji(emojiToDraw.emoji());
            emojis.add(emojiToDraw.emoji());

            assertThat(sdkVersion).isGreaterThanOrEqualTo(sinceVersionOfEmoji);
        }
    }

    private void verifyUnique(List<EmojiToDraw> emojisToDraw) {
        Set<String> emojis = new HashSet<>();
        for (EmojiToDraw emojiToDraw : emojisToDraw) {
            emojis.add(emojiToDraw.emoji());
        }

        assertThat(emojis.size()).isEqualTo(emojisToDraw.size());
    }

    private int getVersionOfEmoji(String emoji) {
        for (Emoji e : emojiList) {
            if (e.emoji.equals(emoji)) {
                return e.sinceVersion;
            }
        }
        return Integer.MAX_VALUE;
    }


}

