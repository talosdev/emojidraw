package app.we.go.emojidraw.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.we.go.emojidraw.model.Emoji;

/**
 * {@link EmojiToDrawProvider} that provides challenges that match a minimum sdk version specified.
 */
public class RandomEmojiToDrawProvider implements EmojiToDrawProvider {

    private final Random random;
    private final List<Emoji> emojiList;
    private final int sdkVersion;
    private static final int MAX_TRIES = 1000;


    public RandomEmojiToDrawProvider(List<Emoji> emojiList, int sdkVersion) {
        this.emojiList = emojiList;
        this.sdkVersion = sdkVersion;
        random = new Random();
    }
    

    @Override
    public List<EmojiToDraw> provide(int n) {
        List<EmojiToDraw> list = new ArrayList<>();

       for (int i=0; i<n; i++) {
           list.add(getEmojiChallengeNotInList(list));
       }

       return list;
    }

    private EmojiToDraw getEmojiChallengeNotInList(List<EmojiToDraw> list) {
        int tries = 0;
        EmojiToDraw emojiToDraw;
        do {
            emojiToDraw = provideOne();
            tries ++;
        } while(list.contains(emojiToDraw) && tries < MAX_TRIES); // avoid going into infinite loop
        // might include duplicates if requested number of emojis is too large

        return emojiToDraw;
    }

    private EmojiToDraw provideOne() {
        Emoji emoji;
        do {
            emoji = emojiList.get(random.nextInt(emojiList.size()));
        } while (!versionSatisfied(emoji));


        return EmojiToDraw.create(emoji.description, emoji.emoji);
    }

    private boolean versionSatisfied(Emoji emoji) {
        return emoji.sinceVersion <= sdkVersion;
    }
}
