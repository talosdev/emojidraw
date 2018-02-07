package app.we.go.emojidraw;

import app.we.go.emojidraw.arch.di.ApplicationComponent;
import app.we.go.emojidraw.arch.di.DaggerTestApplicationComponent;

public class TestApplication extends ThisApplication {

    @Override
    protected ApplicationComponent createComponent() {
        return DaggerTestApplicationComponent.builder()
                .build();

    }
}
