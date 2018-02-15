package app.we.go.emojidraw.arch.di;

import app.we.go.emojidraw.arch.di.features.practice.PracticeComponent;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {TestPracticeModule.class})
public interface TestPracticeComponent extends PracticeComponent{

    @PracticeDuration
    Integer practiceDuration();
}
