package app.we.go.emojidraw.arch.di.features.practice;

import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.features.practice.PracticeActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {PracticeMvpModule.class,
                PracticeConfigurationModule.class})
public interface PracticeComponent extends AndroidInjector<PracticeActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<PracticeActivity> {}
}
