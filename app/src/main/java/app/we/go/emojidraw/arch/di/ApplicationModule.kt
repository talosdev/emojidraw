package app.we.go.emojidraw.arch.di

import android.content.Context
import app.we.go.emojidraw.R
import app.we.go.emojidraw.arch.di.qualifier.CanvasSize
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {


    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }



    @Provides
    @Singleton
    @CanvasSize
    fun provideCanvasSize(): Int {
        return context.resources.getDimensionPixelSize(R.dimen.drawing_area)
    }

}