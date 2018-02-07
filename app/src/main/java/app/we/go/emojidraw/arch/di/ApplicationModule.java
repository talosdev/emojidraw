package app.we.go.emojidraw.arch.di;

import android.content.Context;

import javax.inject.Singleton;

import app.we.go.emojidraw.R;
import app.we.go.emojidraw.arch.di.qualifier.CanvasSize;
import dagger.Module;
import dagger.Provides;

/**
 * Provides general, cross-application dependencies
 */
@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(final Context context) {
        this.context = context;
    }


    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    @CanvasSize
    Integer provideCanvasSize() {
        return context.getResources().getDimensionPixelSize(R.dimen.drawing_area);
    }


}

