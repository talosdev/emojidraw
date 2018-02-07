package app.we.go.emojidraw.arch.di;

import javax.inject.Singleton;

import app.we.go.emojidraw.arch.di.features.practice.PracticeComponent;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,
        ServiceModule.class})
public interface ApplicationComponent {

    PracticeComponent plusPracticeComponent();

}
