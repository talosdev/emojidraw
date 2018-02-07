package app.we.go.emojidraw.arch.di.features.practice;

import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.data.EmojiToDrawProvider;
import app.we.go.emojidraw.data.FixedEmojiToDrawProvider;
import app.we.go.emojidraw.features.practice.PracticeContract;
import app.we.go.emojidraw.features.practice.PracticePresenter;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class PracticeMvpModule {

    @Binds
    @ActivityScope
    abstract PracticeContract.Presenter providePracticePresenter(PracticePresenter presenter);


    @Provides
    @ActivityScope
    static EmojiToDrawProvider provideVersionBasedEmojiToDrawProvider() {
        return new FixedEmojiToDrawProvider();
    }


}
