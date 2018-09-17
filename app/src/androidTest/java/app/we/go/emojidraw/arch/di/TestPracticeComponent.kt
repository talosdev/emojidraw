package app.we.go.emojidraw.arch.di

import app.we.go.emojidraw.arch.di.features.practice.PracticeComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [TestPracticeModule::class])
interface TestPracticeComponent : PracticeComponent {

    @PracticeDuration
    fun practiceDuration(): Int?
}
