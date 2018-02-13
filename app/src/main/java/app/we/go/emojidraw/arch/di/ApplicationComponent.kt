package app.we.go.emojidraw.arch.di

import app.we.go.emojidraw.arch.di.features.practice.PracticeComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), (ServiceModule::class)])
interface ApplicationComponent {

    fun plusPracticeComponent(): PracticeComponent

}
