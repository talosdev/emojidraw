package app.we.go.emojidraw.arch.di;

import android.content.Context;

import javax.inject.Singleton;

import app.we.go.emojidraw.ThisApplication;
import app.we.go.emojidraw.arch.di.features.practice.PracticeModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        ServiceModule.class,
        PracticeModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        ApplicationComponent build();
    }

    void inject(ThisApplication thisApplication);
}
