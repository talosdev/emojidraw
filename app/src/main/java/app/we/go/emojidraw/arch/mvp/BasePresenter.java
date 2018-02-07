package app.we.go.emojidraw.arch.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;


public class BasePresenter<V extends MVP.View> implements MVP.Presenter<V> {

    @Nullable
    protected V view;

    final protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public final void bindView(@NonNull final V view) {
        this.view = view;
        init();
    }

    protected void init() {

    }

    @Override
    public void unbindView() {
        view = null;
        disposables.clear();
    }

    @Override
    public boolean isViewBound() {
        return (view != null);
    }
}
