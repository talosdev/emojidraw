package app.we.go.emojidraw.api

import app.we.go.emojidraw.arch.di.qualifier.CanvasSize
import app.we.go.emojidraw.model.Stroke
import io.reactivex.Single
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.Collections.emptyList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmojiDetectionProvider
@Inject constructor(private val service: EmojiDetectionService,
                    @CanvasSize private val width: Int,
                    @CanvasSize private val height: Int) {


    fun getEmojis(strokes: List<Stroke>): Single<List<String>> {
        val req = EmojiDetectionRequest.Builder()
                .width(width)
                .height(height)
                .setStrokes(convertStrokesToArray(strokes))
                .build()

        return service.detect(req)
                .map(::extractEmojiArrayFromResponse)
    }

    private fun convertStrokesToArray(strokes: List<Stroke>): Array<Array<IntArray>?> {
        val arr = arrayOfNulls<Array<IntArray>>(strokes.size)
        for (i in strokes.indices) {
            val stroke = strokes[i]
            val x = IntArray(stroke.xcoords.size)
            val y = IntArray(stroke.ycoords.size)
            for (j in 0..x.size) {
                x[j] = stroke.xcoords[i]
                y[j] = stroke.ycoords[i]
            }
            val z = IntArray(0)

            arr[i] = arrayOf(x, y, z)
        }
        return arr
    }


    /**
     * The response that the API returns is not a nice JSON object with named attributes, so we can't
     * really use gson to parse it, we need to parse it manually
     * ["SUCCESS",[["b414e3d9cb625ee2",["👄","🛁","🛀","🚢","🍪","📗","🍋","📙","✏️","👊"],[],{"is_html_escaped":false}]]]
     * @param responseBody
     * @return
     * @throws IOException
     */
    private fun extractEmojiArrayFromResponse(responseBody: ResponseBody): List<String> {
        val string = responseBody.string()
        val arrayString = "{\"data\": $string }"

        try {
            val json = JSONObject(arrayString)
            val array = json.getJSONArray("data")

            val status = array.getString(0)
            if (status != "SUCCESS") {
                return emptyList()
            }

            val second = array.getJSONArray(1)
            val emojiArray = second.getJSONArray(0).getJSONArray(1)

            return (0..emojiArray.length()).map { emojiArray.getString(it) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return emptyList()
    }

}
