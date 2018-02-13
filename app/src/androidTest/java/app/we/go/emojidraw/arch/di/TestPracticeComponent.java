package app.we.go.emojidraw.arch.di;

import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.features.practice.PracticeActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {TestPracticeModule.class})
public interface TestPracticeComponent extends AndroidInjector<PracticeActivity> {
    
    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<PracticeActivity> {}
}
