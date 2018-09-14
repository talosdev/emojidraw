package app.we.go.emojidraw.arch.di.features.practice

import app.we.go.emojidraw.arch.di.ActivityScope
import app.we.go.emojidraw.features.practice.PracticeActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(PracticeMvpModule::class), (PracticeConfigurationModule::class)])
interface PracticeComponent {

    fun inject(practiceActivity: PracticeActivity)
}
