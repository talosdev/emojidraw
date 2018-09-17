package app.we.go.emojidraw

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.test.runner.AndroidJUnitRunner
import android.util.Log

import java.lang.reflect.Method

/**
 *
 */
class CustomRunner : AndroidJUnitRunner() {

    override fun onStart() {
        //        RxJavaPlugins.setInitComputationSchedulerHandler(
        //                Rx2Idler.create("RxJava 2.x Computation Scheduler"));
        //
        //        RxJavaPlugins.setInitIoSchedulerHandler(
        //                Rx2Idler.create("RxJava 2.x IO Scheduler"));
        //
        //        RxJavaPlugins.setInitNewThreadSchedulerHandler(
        //                Rx2Idler.create("RxJava 2.x New Thread Scheduler"));
        //

        runOnMainSync {
            val app = targetContext.applicationContext
            disableAnimations(app)
        }

        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        enableAnimations(context)
        super.finish(resultCode, results)
    }

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }


    private fun disableAnimations(context: Context) {
        Log.d("TEST", "Disabling animations")
        val permStatus = context.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE)
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            Log.d("TEST", "Have permission")
            setSystemAnimationsScale(0.0f)
        } else {
            Log.d("TEST", "Don't have permission")
        }
    }

    private fun enableAnimations(context: Context) {
        val permStatus = context.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE)
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            setSystemAnimationsScale(1.0f)
        }
    }

    private fun setSystemAnimationsScale(animationScale: Float) {
        try {
            val windowManagerStubClazz = Class.forName("android.view.IWindowManager\$Stub")
            val asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder::class.java)
            val serviceManagerClazz = Class.forName("android.os.ServiceManager")
            val getService = serviceManagerClazz.getDeclaredMethod("getService", String::class.java)
            val windowManagerClazz = Class.forName("android.view.IWindowManager")
            val setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales", FloatArray::class.java)
            val getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales")

            val windowManagerBinder = getService.invoke(null, "window") as IBinder
            val windowManagerObj = asInterface.invoke(null, windowManagerBinder)
            val currentScales = getAnimationScales.invoke(windowManagerObj) as FloatArray
            for (i in currentScales.indices) {
                Log.d("TEST", currentScales[i].toString() + "")
                currentScales[i] = animationScale
            }
            setAnimationScales.invoke(windowManagerObj, currentScales)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}

