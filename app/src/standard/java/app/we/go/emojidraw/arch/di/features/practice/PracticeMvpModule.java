package app.we.go.emojidraw.arch.di.features.practice;

import android.os.Build;

import app.we.go.emojidraw.arch.di.ActivityScope;
import app.we.go.emojidraw.data.EmojiToDrawProvider;
import app.we.go.emojidraw.data.RandomEmojiToDrawProvider;
import app.we.go.emojidraw.features.practice.PracticeContract;
import app.we.go.emojidraw.features.practice.PracticePresenter;
import app.we.go.emojidraw.util.EmojiFileReader;
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
    static EmojiToDrawProvider provideVersionBasedEmojiToDrawProvider(EmojiFileReader emojiFileReader) {
        return new RandomEmojiToDrawProvider(emojiFileReader.getEmojiList(), Build.VERSION.SDK_INT);
    }


}
