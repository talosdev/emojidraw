package app.we.go.emojidraw.api;


import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class EmojiDetectionRequest {

    private static final String OPTIONS = "enable_pre_space";
    private static final String LANGUAGE = "emoji_rotated";

    String options;

    List<Request> requests;

    private static class Request {
        @SerializedName("writing_guide")
        WritingGuide writingGuide;

        int[][][] ink;

        String language;

        private static class WritingGuide {
            @SerializedName("writing_area_width")
            public final String width;
            @SerializedName("writing_area_height")
            public final String height;

            public WritingGuide(String width, String height) {
                this.width = width;
                this.height = height;
            }
        }
    }


    public static class Builder {
        private int width;
        private int height;
        private int[][][] strokes;

        public Builder() {        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        Builder setStrokes(int[][][] strokes) {
            this.strokes = strokes;
            return this;
        }

        public EmojiDetectionRequest build() {
            EmojiDetectionRequest edr = new EmojiDetectionRequest();
            edr.options = OPTIONS;
            Request request = new Request();
            request.writingGuide = new Request.WritingGuide(String.valueOf(width),String.valueOf(height));
            request.language = LANGUAGE;
            request.ink = strokes;

            edr.requests = Collections.singletonList(request);

            return edr;
        }

    }

}