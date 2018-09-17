package app.we.go.emojidraw.api

import com.google.gson.annotations.SerializedName


class EmojiDetectionRequest(val options: String, val requests: List<Request>) {

    class Request(
        @field:SerializedName("writing_guide")
        val writingGuide: WritingGuide,

        val ink: Array<Array<IntArray>>,

        val language: String? = null) {

        class WritingGuide(@field:SerializedName("writing_area_width")
                           val width: String, @field:SerializedName("writing_area_height")
                           val height: String)

    }

    companion object {

        const val OPTIONS = "enable_pre_space"
        const val LANGUAGE = "emoji_rotated"
    }

    class Builder {
        private var width: Int = 0
        private var height: Int = 0
        private lateinit var strokes: Array<Array<IntArray>>

        fun width(width: Int): Builder {
            this.width = width
            return this
        }

        fun height(height: Int): Builder {
            this.height = height
            return this
        }

        fun setStrokes(strokes: Array<Array<IntArray>>): Builder {
            this.strokes = strokes
            return this
        }

        fun build(): EmojiDetectionRequest {
            requireNotNull(strokes)
            val request = EmojiDetectionRequest.Request(
                writingGuide = EmojiDetectionRequest.Request.WritingGuide(width.toString(), height.toString()),
                language = LANGUAGE,
                ink = strokes)

            return EmojiDetectionRequest(options = OPTIONS, requests = listOf(request))

        }

    }

}





