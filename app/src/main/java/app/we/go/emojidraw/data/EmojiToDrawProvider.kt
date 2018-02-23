package app.we.go.emojidraw.data

interface EmojiToDrawProvider {

    fun provide(n: Int) : List<EmojiToDraw>

}