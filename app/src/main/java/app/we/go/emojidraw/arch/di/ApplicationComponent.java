package app.we.go.emojidraw.arch.di;

import javax.inject.Singleton;

import app.we.go.emojidraw.ThisApplication;
import app.we.go.emojidraw.arch.di.features.practice.PracticeModule;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        ServiceModule.class,
        PracticeModule.class})
public interface ApplicationComponent {

    void inject(ThisApplication thisApplication);
}
