package app.we.go.emojidraw.arch.di;

import android.app.Activity;

import app.we.go.emojidraw.features.practice.PracticeActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {TestPracticeComponent.class})
public abstract class TestPracticeActivityBinding {


    @Binds
    @IntoMap
    @ActivityKey(PracticeActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindYourActivityInjectorFactory(TestPracticeComponent.Builder builder);



}
