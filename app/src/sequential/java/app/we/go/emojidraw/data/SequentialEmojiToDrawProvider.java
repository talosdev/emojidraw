package app.we.go.emojidraw.data;

import java.util.ArrayList;
import java.util.List;

import app.we.go.emojidraw.features.practice.SequentialSharedPrefsHelper;
import app.we.go.emojidraw.model.Emoji;

public class SequentialEmojiToDrawProvider implements EmojiToDrawProvider {

    private SequentialSharedPrefsHelper sequentialSharedPrefsHelper;
    private final List<Emoji> emojiList;
    private final int sdkVersion;

    public SequentialEmojiToDrawProvider(SequentialSharedPrefsHelper sequentialSharedPrefsHelper, List<Emoji> list, int sdkVersion) {
        this.sequentialSharedPrefsHelper = sequentialSharedPrefsHelper;
        this.emojiList = list;
        this.sdkVersion = sdkVersion;
    }

    @Override
    public List<EmojiToDraw> provide(int n) {
        List<EmojiToDraw> emojiToDrawList = new ArrayList<>();

        int j = 0; // elements in the array we are creating and that we will return
        int index = sequentialSharedPrefsHelper.getCurrentIndex();
        int i = index;  // index in the emojiList
        while(j < n && i<emojiList.size()) {
            Emoji e = emojiList.get(i);
            if (e.sinceVersion <= sdkVersion) {
                j++;
                emojiToDrawList.add(EmojiToDraw.create(e.description, e.emoji));
            }
            i++;
        }

        return emojiToDrawList;
    }
}
