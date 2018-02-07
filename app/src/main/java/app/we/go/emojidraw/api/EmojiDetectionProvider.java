package app.we.go.emojidraw.api;

import android.support.annotation.VisibleForTesting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.we.go.emojidraw.arch.di.qualifier.CanvasSize;
import app.we.go.emojidraw.model.Stroke;
import io.reactivex.Single;
import okhttp3.ResponseBody;

/**
 * A facade to the {@link EmojiDetectionService}
 */
@Singleton
public class EmojiDetectionProvider {

    private final EmojiDetectionService service;
    private final int width;
    private final int height;

    @Inject
    public EmojiDetectionProvider(EmojiDetectionService service,
                                  @CanvasSize int width, @CanvasSize int height) {
        this.service = service;
        this.width = width;
        this.height = height;
    }


    public Single<List<String>> getEmojis(List<Stroke> strokes) {
        EmojiDetectionRequest req = new EmojiDetectionRequest.Builder()
                .width(width)
                .height(height)
                .setStrokes(convertStrokesToArray(strokes))
                .build();

        return service.detect(req)
                .map(this::extractEmojiArrayFromResponse);
    }

    @VisibleForTesting
    Single<List<String>> getEmojis(int[][][] strokes) {
        EmojiDetectionRequest req = new EmojiDetectionRequest.Builder()
                .width(width)
                .height(height)
                .setStrokes(strokes)
                .build();

        return service.detect(req)
                .map(this::extractEmojiArrayFromResponse);
    }


    private int[][][] convertStrokesToArray(final List<Stroke> strokes) {
        int[][][] arr = new int[strokes.size()][][];
        for (int i = 0; i < strokes.size(); i++) {
            Stroke stroke = strokes.get(i);
            int[] xarr = new int[stroke.getXcoords().size()];
            int[] yarr = new int[stroke.getYcoords().size()];
            for (int j = 0; j < stroke.getXcoords().size(); j++) {
                xarr[j] = stroke.getXcoords().get(j);
                yarr[j] = stroke.getYcoords().get(j);
            }
            int[] zarr = new int[0];

            arr[i] = new int[][]{xarr, yarr, zarr};
        }

        return arr;
    }

    /**
     * The response that the API returns is not a nice JSON object with named attributes, so we can't
     * really use gson to parse it, we need to parse it manually
     * ["SUCCESS",[["b414e3d9cb625ee2",["👄","🛁","🛀","🚢","🍪","📗","🍋","📙","✏️","👊"],[],{"is_html_escaped":false}]]]
     * @param responseBody
     * @return
     * @throws IOException
     */
    private List<String> extractEmojiArrayFromResponse(ResponseBody responseBody) throws IOException {
        final String string = responseBody.string();
        String arrayString = "{\"data\": " + string + "}";

        try {
            JSONObject json = new JSONObject(arrayString);
            final JSONArray array = json.getJSONArray("data");

            final String status = array.getString(0);
            if (!status.equals("SUCCESS")) {
                return Collections.emptyList();
            }

            JSONArray second = array.getJSONArray(1);
            final JSONArray emojiArray = second.getJSONArray(0).getJSONArray(1);
            List<String> emojis = new ArrayList<>();
            for (int i=0; i<emojiArray.length(); i++) {
                emojis.add(emojiArray.getString(i));
            }

            return emojis;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
