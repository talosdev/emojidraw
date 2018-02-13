package app.we.go.emojidraw.arch.di;

import android.content.Context;

import javax.inject.Singleton;

import app.we.go.emojidraw.TestApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules={
        AndroidInjectionModule.class,
        ApplicationModule.class,
       TestPracticeActivityBinding.class})
public interface TestApplicationComponent extends ApplicationComponent {

    
    @Component.Builder
    interface Builder {
        @BindsInstance
        TestApplicationComponent.Builder context(Context context);
        TestApplicationComponent build();
    }

    void inject(TestApplication thisApplication);
}
