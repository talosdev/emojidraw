package app.we.go.emojidraw.arch.di.features.practice

import android.os.Build

import app.we.go.emojidraw.arch.di.ActivityScope
import app.we.go.emojidraw.data.EmojiToDrawProvider
import app.we.go.emojidraw.data.RandomEmojiToDrawProvider
import app.we.go.emojidraw.features.practice.PracticeContract
import app.we.go.emojidraw.features.practice.PracticePresenter
import app.we.go.emojidraw.util.EmojiFileReader
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PracticeMvpModule {

    @Binds
    @ActivityScope
    abstract fun providePracticePresenter(presenter: PracticePresenter): PracticeContract.Presenter

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideVersionBasedEmojiToDrawProvider(emojiFileReader: EmojiFileReader): EmojiToDrawProvider {
            return RandomEmojiToDrawProvider(emojiFileReader.emojiList, Build.VERSION.SDK_INT)
        }
    }


}
