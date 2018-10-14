package app.we.go.emojidraw

import android.app.Application
import android.content.Context
import app.we.go.emojidraw.arch.di.ApplicationComponent
import app.we.go.emojidraw.arch.di.ApplicationModule
import app.we.go.emojidraw.arch.di.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


open class ThisApplication: Application() {

    private lateinit var component: ApplicationComponent

    companion object {
        fun getComponent(context: Context): ApplicationComponent {
            return (context.applicationContext as ThisApplication).component
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        component = createComponent()

    }

    protected open fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }



}