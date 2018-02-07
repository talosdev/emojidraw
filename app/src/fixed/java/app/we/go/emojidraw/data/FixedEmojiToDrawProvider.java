package app.we.go.emojidraw.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link EmojiToDrawProvider} that always returns a fixed list of emojis, to
 * facilitate testing.
 */
public class FixedEmojiToDrawProvider implements EmojiToDrawProvider {
    @Override
    public List<EmojiToDraw> provide(int n) {
        return getClockEmojis();
    }

    /**
     * I can draw these easily
     * @return
     */
    @NonNull
    private List<EmojiToDraw> getClockEmojis() {
        List<EmojiToDraw> list = new ArrayList<>();
        list.add(EmojiToDraw.create("one o’clock", "🕐"));
        list.add(EmojiToDraw.create("one-thirty", "🕜"));
        list.add(EmojiToDraw.create("two o’clock","🕑"));
        list.add(EmojiToDraw.create("two-thirty", "🕝"));
        list.add(EmojiToDraw.create( "three o’clock", "🕒"));
        list.add(EmojiToDraw.create( "three-thirty","🕞"));
        list.add(EmojiToDraw.create("four o’clock","🕓"));
        list.add(EmojiToDraw.create("four-thirty","🕟"));
        list.add(EmojiToDraw.create("five o’clock", "🕔"));
        list.add(EmojiToDraw.create("five-thirty", "🕠"));
        return list;
    }


    /**
     * Returns a list of all emojis that end with the variant selector character, to make
     * sure that they are displayed and recognized correctly.
     * @return
     */
    private List<EmojiToDraw> getEmojisEndingInVariantSelector() {
        List<EmojiToDraw> list = new ArrayList<>();


        list.add(EmojiToDraw.create("sun", "☀️"));
        list.add(EmojiToDraw.create("classical building", "\uD83C\uDFDB️"));
        list.add(EmojiToDraw.create("desert", "🏜️"));
        list.add(EmojiToDraw.create("desert island", "🏝️"));
        list.add(EmojiToDraw.create("motorcycle", "🏍️"));
        list.add(EmojiToDraw.create("spider web", "🕸️"));
        list.add(EmojiToDraw.create("eye", "👁️"));
        list.add(EmojiToDraw.create("sun behind rain cloud", "🌦️"));
        list.add(EmojiToDraw.create("building construction", "🏗️"));
        list.add(EmojiToDraw.create("beach with umbrella", "🏖️"));
        list.add(EmojiToDraw.create("spiral notepad", "🗒️"));
        list.add(EmojiToDraw.create("hand with fingers splayed", "🖐️"));
        list.add(EmojiToDraw.create("oil drum", "🛢️"));
        list.add(EmojiToDraw.create("world map", "🗺️"));
        list.add(EmojiToDraw.create("computer mouse", "🖱️"));
        list.add(EmojiToDraw.create("stadium", "🏟️"));
        list.add(EmojiToDraw.create("sunglasses", "🕶️"));
        list.add(EmojiToDraw.create("hammer and wrench", "🛠️"));
        list.add(EmojiToDraw.create("racing car", "🏎️"));
        list.add(EmojiToDraw.create("old key", "🗝️"));
        list.add(EmojiToDraw.create("thermometer", "🌡️"));
        list.add(EmojiToDraw.create("cloud with rain", "🌧️"));
        list.add(EmojiToDraw.create("file cabinet", "🗄️"));

        list.add(EmojiToDraw.create("level slider", "🎚️"));
        list.add(EmojiToDraw.create("printer", "🖨️"));
        list.add(EmojiToDraw.create("pause button", "⏸️"));
        list.add(EmojiToDraw.create("snowflake", "❄️"));
        list.add(EmojiToDraw.create("sun behind small cloud", "🌤️"));
        list.add(EmojiToDraw.create("person golfing", "🏌️"));

        list.add(EmojiToDraw.create("joystick", "🕹️"));
        list.add(EmojiToDraw.create("wastebasket", "🗑️"));
        list.add(EmojiToDraw.create("couch and lamp", "🛋️"));
        list.add(EmojiToDraw.create("bed", "🛏️"));
        list.add(EmojiToDraw.create("cloud with lightning", "🌩️"));
        list.add(EmojiToDraw.create("hot pepper", "🌶️"));
        list.add(EmojiToDraw.create("hole", "🕳️"));
        list.add(EmojiToDraw.create("national park", "🏞️"));
        list.add(EmojiToDraw.create("paintbrush", "🖌️"));
        list.add(EmojiToDraw.create("telephone", "☎️"));
        list.add(EmojiToDraw.create("control knobs", "🎛️"));
        list.add(EmojiToDraw.create("mantelpiece clock", "🕰️"));


        list.add(EmojiToDraw.create("camping", "🏕️"));
        list.add(EmojiToDraw.create("shield", "🛡️"));
        list.add(EmojiToDraw.create("ballot box with ballot", "🗳️"));
        list.add(EmojiToDraw.create("houses", "🏘️"));
        list.add(EmojiToDraw.create("fork and knife with plate", "🍽️"));
        list.add(EmojiToDraw.create("spider", "🕷️"));
        list.add(EmojiToDraw.create("person lifting weights", "🏋️"));
        list.add(EmojiToDraw.create("victory hand", "✌️"));
        list.add(EmojiToDraw.create("bellhop bell", "🛎️"));
        list.add(EmojiToDraw.create("railway track", "🛤️"));
        list.add(EmojiToDraw.create("cityscape", "🏙️"));
        list.add(EmojiToDraw.create("satellite", "🛰️"));
        list.add(EmojiToDraw.create("cloud with snow", "🌨️"));
        list.add(EmojiToDraw.create("tornado", "🌪️"));

        return list;
    }
}
