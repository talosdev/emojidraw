package app.we.go.emojidraw.arch.di.features.practice;

import app.we.go.emojidraw.arch.di.qualifier.PracticeDuration;
import app.we.go.emojidraw.arch.di.qualifier.PracticeSize;
import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

@ActivityScope
@Module
public class PracticeConfigurationModule {

    @Provides
    @ActivityScope
    @PracticeDuration
    static Integer providePracticeDuration() {
        return 60;
    }

    @Provides
    @PracticeSize
    @ActivityScope
    static Integer providePracticeSize() {
        return 10;
    }
}
