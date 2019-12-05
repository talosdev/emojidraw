package app.we.go.emojidraw.arch.di

import app.we.go.emojidraw.api.BASE_URL
import app.we.go.emojidraw.api.EmojiDetectionService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    @Provides
    @Singleton
    fun provideEmojiDetectionService(retrofit: Retrofit): EmojiDetectionService {
        return retrofit.create(EmojiDetectionService::class.java)
    }


}

