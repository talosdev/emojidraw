package app.we.go.emojidraw.util

import android.content.Context
import app.we.go.emojidraw.model.Emoji
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.io.Reader
import javax.inject.Inject

class EmojiFileReader @Inject constructor(context: Context) {

    val emojiList = ArrayList<Emoji>()

    init {
        context.assets.open("emoji-selected.json").use {
            val reader = InputStreamReader(it)
            val gson = GsonBuilder().create()
            emojiList.addAll(gson.fromJson(reader))
        }
    }

    inline fun <reified T> Gson.fromJson(reader: Reader) = this.fromJson<T>(reader, object: TypeToken<T>() {}.type)



}