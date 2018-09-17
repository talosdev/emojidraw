package app.we.go.emojidraw.arch.di.features.practice

import app.we.go.emojidraw.arch.di.ActivityScope
import app.we.go.emojidraw.data.EmojiToDrawProvider
import app.we.go.emojidraw.data.FixedEmojiToDrawProvider
import app.we.go.emojidraw.features.practice.PracticeContract
import app.we.go.emojidraw.features.practice.PracticePresenter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PracticeMvpModule {

    @Binds
    @ActivityScope
    internal abstract fun providePracticePresenter(presenter: PracticePresenter): PracticeContract.Presenter

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        internal fun provideVersionBasedEmojiToDrawProvider(): EmojiToDrawProvider {
            return FixedEmojiToDrawProvider()
        }
    }


}
