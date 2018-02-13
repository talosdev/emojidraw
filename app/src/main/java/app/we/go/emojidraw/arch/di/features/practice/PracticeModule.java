package app.we.go.emojidraw.arch.di.features.practice;

import android.app.Activity;

import app.we.go.emojidraw.features.practice.PracticeActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {PracticeComponent.class})
public abstract class PracticeModule {


    @Binds
    @IntoMap
    @ActivityKey(PracticeActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindYourActivityInjectorFactory(PracticeComponent.Builder builder);



}
