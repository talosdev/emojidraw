package app.we.go.emojidraw;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import app.we.go.emojidraw.arch.di.ApplicationComponent;
import app.we.go.emojidraw.arch.di.ApplicationModule;
import app.we.go.emojidraw.arch.di.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class ThisApplication extends Application implements HasActivityInjector {

    private ApplicationComponent component;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        component = createComponent();
        component.inject(this);
    }

    protected ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .context(this)
                .build();
    }


    public static ApplicationComponent getComponent(Context context) {
     return ((ThisApplication) context.getApplicationContext()).component;
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }


}
