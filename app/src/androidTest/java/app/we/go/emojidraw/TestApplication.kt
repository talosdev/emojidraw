package app.we.go.emojidraw

import app.we.go.emojidraw.arch.di.ApplicationComponent
import app.we.go.emojidraw.arch.di.DaggerTestApplicationComponent

class TestApplication : ThisApplication() {

    override fun createComponent(): ApplicationComponent {
        return DaggerTestApplicationComponent.builder()
                .build()

    }
}
