package app.we.go.emojidraw.arch.di

import app.we.go.emojidraw.features.practice.PracticeContract
import app.we.go.emojidraw.features.practice.StubPracticePresenter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class TestPracticeModule {

    @Binds
    @ActivityScope
    internal abstract fun providePracticePresenter(presenter: StubPracticePresenter): PracticeContract.Presenter


    @Module
    companion object {

        @Provides
        @ActivityScope
        @PracticeDuration
        @JvmStatic
        internal fun providePracticeDuration(): Int {
            return 2
        }
    }


}