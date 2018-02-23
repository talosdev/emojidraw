package app.we.go.emojidraw.api;

import android.util.Log;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import io.reactivex.observers.TestObserver;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Test that serves for API exploration, not an automated test and so it is ignored (meant to be
 * run manually and check the output).
 */
@Ignore("not automated")
public class EmojiDetectionProviderTest {


    private EmojiDetectionProvider provider;

    @Before
    public void setUp() throws Exception {

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        EmojiDetectionService service = retrofit.create(EmojiDetectionService.class);
        provider = new EmojiDetectionProvider(service, 800, 800);
    }

    @Test
    public void name() throws Exception {
         int[][][] strokes = new int[][][]{{
                {68, 68, 69, 70, 70, 70, 70, 71, 71, 71, 71, 71, 73, 73, 74, 75, 76, 76, 76, 78, 79, 79, 80, 80, 82, 84, 85, 87, 89, 94, 98, 102, 113, 118, 130, 147, 155, 174, 189, 213, 232, 246, 251, 255, 260, 262, 265, 269, 275, 277, 280, 282, 285, 293, 297, 308, 313, 317, 324, 328, 335, 341, 350, 354, 356, 356, 357, 358, 358, 358, 358, 358, 360, 360, 361, 361, 361, 361, 362, 362, 363, 369, 371, 371, 372, 372},
                {246, 247, 248, 251, 252, 253, 254, 257, 260, 265, 266, 267, 273, 274, 278, 283, 286, 291, 292, 299, 301, 304, 307, 311, 313, 319, 321, 324, 327, 335, 339, 344, 353, 358, 368, 380, 384, 390, 394, 394, 397, 401, 402, 402, 402, 402, 402, 402, 401, 400, 397, 396, 395, 391, 389, 383, 381, 379, 376, 375, 368, 364, 356, 352, 344, 341, 338, 333, 328, 326, 321, 317, 311, 305, 294, 288, 280, 271, 265, 256, 249, 239, 235, 231, 225, 221},
                {}},
                {
                {372, 372, 372, 371, 366, 357, 346, 324, 308, 289, 270, 246, 245, 241, 237, 228, 217, 206, 195, 192, 189, 186, 178, 170, 161, 157, 156, 155, 152, 151, 144, 136, 128, 114, 109, 108, 107, 104, 101, 98, 96, 90, 87, 82, 67, 59, 58, 56, 56, 59, 62, 63, 63, 63, 63, 63, 64, 64, 64, 64, 64, 64, 64},
                {221, 219, 218, 217, 217, 217, 217, 217, 217, 215, 215, 212, 212, 211, 211, 211, 211, 211, 211, 211, 212, 212, 212, 213, 215, 215, 215, 215, 215, 215, 215, 216, 217, 217, 217, 217, 217, 217, 214, 213, 213, 213, 213, 213, 211, 211, 211, 213, 214, 217, 221, 222, 223, 227, 231, 232, 233, 235, 236, 237, 240, 241, 242},
                {}},

                {{134, 134, 134, 135, 135}, {267, 270, 271, 274, 276}, {}},
                {{194, 195, 196, 196, 197, 197}, {322, 324, 328, 333, 335, 336}, {}},
                {{297, 296, 296, 296, 296, 296, 296}, {266, 266, 267, 268, 269, 270, 276}, {}}};



        final TestObserver<List<String>> sub = provider.getEmojis(strokes)
                .test()
                .assertComplete();

        final List<String> emojis = sub.values().get(0);

        for (String emoji : emojis) {
            Log.d("TEST", emoji);
        }
    }
}
