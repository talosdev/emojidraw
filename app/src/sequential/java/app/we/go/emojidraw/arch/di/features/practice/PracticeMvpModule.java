package app.we.go.emojidraw.arch.di.features.practice;

import android.os.Build;

import app.we.go.emojidraw.arch.di.scope.ActivityScope;
import app.we.go.emojidraw.data.EmojiToDrawProvider;
import app.we.go.emojidraw.data.SequentialEmojiToDrawProvider;
import app.we.go.emojidraw.features.practice.PracticeContract;
import app.we.go.emojidraw.features.practice.SequentialPracticePresenter;
import app.we.go.emojidraw.features.practice.SequentialSharedPrefsHelper;
import app.we.go.emojidraw.util.EmojiFileReader;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class PracticeMvpModule {

    @Binds
    @ActivityScope
    abstract PracticeContract.Presenter providePracticePresenter(SequentialPracticePresenter presenter);


    @Provides
    @ActivityScope
    static EmojiToDrawProvider provideVersionBasedEmojiToDrawProvider(SequentialSharedPrefsHelper sharedPrefs, EmojiFileReader emojiFileReader) {
        return new SequentialEmojiToDrawProvider(sharedPrefs, emojiFileReader.getList(), Build.VERSION.SDK_INT);
    }

}
