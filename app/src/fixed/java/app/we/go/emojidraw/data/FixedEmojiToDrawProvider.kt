package app.we.go.emojidraw.data

/**
 * An [EmojiToDrawProvider] that always returns a fixed list of emojis, to
 * facilitate testing.
 */
class FixedEmojiToDrawProvider : EmojiToDrawProvider {

    private val clockEmojis: List<EmojiToDraw> = listOf(
        EmojiToDraw("one o’clock", "🕐"),
        EmojiToDraw("one-thirty", "🕜"),
        EmojiToDraw("two o’clock", "🕑"),
        EmojiToDraw("two-thirty", "🕝"),
        EmojiToDraw("three o’clock", "🕒"),
        EmojiToDraw("three-thirty", "🕞"),
        EmojiToDraw("four o’clock", "🕓"),
        EmojiToDraw("four-thirty", "🕟"),
        EmojiToDraw("five o’clock", "🕔"),
        EmojiToDraw("five-thirty", "🕠")
    )

    override fun provide(n: Int): List<EmojiToDraw> {
        return clockEmojis
    }

    /**
     * Returns a list of all emojis that end with the variant selector character, to make
     * sure that they are displayed and recognized correctly.
     * Supposed to be an alternative list that `provide` can return - manual switch is required.
     * @return
     */
    private val emojisEndingInVariantSelector: List<EmojiToDraw> by lazy {
        listOf(
            EmojiToDraw("sun", "☀️"),
            EmojiToDraw("classical building", "\uD83C\uDFDB️"),
            EmojiToDraw("desert", "🏜️"),
            EmojiToDraw("desert island", "🏝️"),
            EmojiToDraw("motorcycle", "🏍️"),
            EmojiToDraw("spider web", "🕸️"),
            EmojiToDraw("eye", "👁️"),
            EmojiToDraw("sun behind rain cloud", "🌦️"),
            EmojiToDraw("building construction", "🏗️"),
            EmojiToDraw("beach with umbrella", "🏖️"),
            EmojiToDraw("spiral notepad", "🗒️"),
            EmojiToDraw("hand with fingers splayed", "🖐️"),
            EmojiToDraw("oil drum", "🛢️"),
            EmojiToDraw("world map", "🗺️"),
            EmojiToDraw("computer mouse", "🖱️"),
            EmojiToDraw("stadium", "🏟️"),
            EmojiToDraw("sunglasses", "🕶️"),
            EmojiToDraw("hammer and wrench", "🛠️"),
            EmojiToDraw("racing car", "🏎️"),
            EmojiToDraw("old key", "🗝️"),
            EmojiToDraw("thermometer", "🌡️"),
            EmojiToDraw("cloud with rain", "🌧️"),
            EmojiToDraw("file cabinet", "🗄️"),

            EmojiToDraw("level slider", "🎚️"),
            EmojiToDraw("printer", "🖨️"),
            EmojiToDraw("pause button", "⏸️"),
            EmojiToDraw("snowflake", "❄️"),
            EmojiToDraw("sun behind small cloud", "🌤️"),
            EmojiToDraw("person golfing", "🏌️"),

            EmojiToDraw("joystick", "🕹️"),
            EmojiToDraw("wastebasket", "🗑️"),
            EmojiToDraw("couch and lamp", "🛋️"),
            EmojiToDraw("bed", "🛏️"),
            EmojiToDraw("cloud with lightning", "🌩️"),
            EmojiToDraw("hot pepper", "🌶️"),
            EmojiToDraw("hole", "🕳️"),
            EmojiToDraw("national park", "🏞️"),
            EmojiToDraw("paintbrush", "🖌️"),
            EmojiToDraw("telephone", "☎️"),
            EmojiToDraw("control knobs", "🎛️"),
            EmojiToDraw("mantelpiece clock", "🕰️"),


            EmojiToDraw("camping", "🏕️"),
            EmojiToDraw("shield", "🛡️"),
            EmojiToDraw("ballot box with ballot", "🗳️"),
            EmojiToDraw("houses", "🏘️"),
            EmojiToDraw("fork and knife with plate", "🍽️"),
            EmojiToDraw("spider", "🕷️"),
            EmojiToDraw("person lifting weights", "🏋️"),
            EmojiToDraw("victory hand", "✌️"),
            EmojiToDraw("bellhop bell", "🛎️"),
            EmojiToDraw("railway track", "🛤️"),
            EmojiToDraw("cityscape", "🏙️"),
            EmojiToDraw("satellite", "🛰️"),
            EmojiToDraw("cloud with snow", "🌨️"),
            EmojiToDraw("tornado", "🌪️")
        )
    }


}
