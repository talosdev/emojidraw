package app.we.go.emojidraw.arch.di.features.practice

import app.we.go.emojidraw.arch.di.PracticeDuration
import app.we.go.emojidraw.arch.di.ActivityScope
import dagger.Module
import dagger.Provides

@Module
object PracticeConfigurationModule {

    @Provides
    @JvmStatic
    @ActivityScope
    @PracticeDuration
    fun providePracticeDuration(): Int {
        return 60
    }

    @Provides
    @JvmStatic
    @ActivityScope
    fun providePracticeSize(): Int {
        return 10
    }
}
