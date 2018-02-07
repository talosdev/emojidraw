package app.we.go.emojidraw.arch.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={ApplicationModule.class})
public interface TestApplicationComponent extends ApplicationComponent {

    TestPracticeComponent plusPracticeComponent();


}
