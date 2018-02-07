package app.we.go.emojidraw.api;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmojiDetectionService {

    String BASE_URL = "https://www.google.com/inputtools/";

    @POST("request?ime=handwriting&app=mobilesearch&cs=1&oe=UTF-8")
    Single<ResponseBody> detect(@Body EmojiDetectionRequest body);

}
