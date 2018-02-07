package app.we.go.emojidraw.arch.mvp;

import android.support.annotation.NonNull;

public interface MVP {

    interface View {

    }

    interface Presenter<V> {

        void bindView(@NonNull V view);

        void unbindView();

        boolean isViewBound();
    }
}
