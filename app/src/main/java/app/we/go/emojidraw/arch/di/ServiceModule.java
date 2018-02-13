package app.we.go.emojidraw.arch.di;

import javax.inject.Singleton;

import app.we.go.emojidraw.api.ApiConstants;
import app.we.go.emojidraw.api.EmojiDetectionService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides dependencies related to the remote image recognition service
 */
@Singleton
@Module
class ServiceModule {

    public ServiceModule() {
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        //noinspection UnnecessaryLocalVariable
        final Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConstants.Companion.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    EmojiDetectionService provideEmojiDetectionService(Retrofit retrofit) {
        return retrofit.create(EmojiDetectionService.class);
    }



}