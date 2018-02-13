package app.we.go.emojidraw;

import android.app.Application;
import android.content.Context;

import app.we.go.emojidraw.arch.di.ApplicationComponent;
import app.we.go.emojidraw.arch.di.ApplicationModule;
import app.we.go.emojidraw.arch.di.DaggerApplicationComponent;


public class ThisApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = createComponent();
    }

    protected ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }


    public static ApplicationComponent getComponent(Context context) {
     return ((ThisApplication) context.getApplicationContext()).component;
    }



}
