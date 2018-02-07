package app.we.go.emojidraw.data;

import com.google.auto.value.AutoValue;

/**
 * Represents an emoji that the user is challenged to draw.
 */
@AutoValue
public abstract class EmojiToDraw {

    public abstract String description();
    public abstract String emoji();

    public static EmojiToDraw create(String description, String emoji) {
        return new AutoValue_EmojiToDraw(description, emoji);
    }


}
