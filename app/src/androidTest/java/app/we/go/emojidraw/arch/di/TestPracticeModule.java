package app.we.go.emojidraw.arch.di;

import app.we.go.emojidraw.arch.di.qualifier.PracticeDuration;
import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.features.practice.PracticeContract;
import app.we.go.emojidraw.features.practice.StubPracticePresenter;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@ActivityScope
@Module
public abstract class TestPracticeModule {

    @Binds
    @ActivityScope
    abstract PracticeContract.Presenter providePracticePresenter(StubPracticePresenter presenter);

    @Provides
    @ActivityScope
    @PracticeDuration
    static Integer providePracticeDuration() {
        return 2;
    }


}