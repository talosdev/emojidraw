package app.we.go.emojidraw.arch.di

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [ApplicationModule::class])
interface TestApplicationComponent : ApplicationComponent {

    override fun plusPracticeComponent(): TestPracticeComponent

}
