package app.we.go.emojidraw.arch.mvp

interface MVP {

    interface View

    interface Presenter<V> {

        fun isViewBound(): Boolean

        fun bindView(view: V)

        fun unbindView()
    }
}
