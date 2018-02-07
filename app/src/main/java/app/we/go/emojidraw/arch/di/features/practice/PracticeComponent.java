package app.we.go.emojidraw.arch.di.features.practice;

import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.features.practice.PracticeActivity;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {app.we.go.emojidraw.arch.di.features.practice.PracticeMvpModule.class,
                PracticeConfigurationModule.class})
public interface PracticeComponent {

    void inject(PracticeActivity practiceActivity);
}
