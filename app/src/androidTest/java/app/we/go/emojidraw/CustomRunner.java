package app.we.go.emojidraw;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import java.lang.reflect.Method;

/**

 */
public class CustomRunner extends AndroidJUnitRunner {

    @Override
    public void onStart() {
//        RxJavaPlugins.setInitComputationSchedulerHandler(
//                Rx2Idler.create("RxJava 2.x Computation Scheduler"));
//
//        RxJavaPlugins.setInitIoSchedulerHandler(
//                Rx2Idler.create("RxJava 2.x IO Scheduler"));
//
//        RxJavaPlugins.setInitNewThreadSchedulerHandler(
//                Rx2Idler.create("RxJava 2.x New Thread Scheduler"));
//

        runOnMainSync(() -> {
            final Context app = getTargetContext().getApplicationContext();
            disableAnimations(app);
        });

        super.onStart();
    }

    @Override
    public void finish(final int resultCode, final Bundle results) {
        enableAnimations(getContext());
        super.finish(resultCode, results);
    }

    @Override
    public Application newApplication(final ClassLoader cl, final String className, final Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestApplication.class.getName(), context);
    }


    void disableAnimations(final Context context) {
        Log.d("TEST", "Disabling animations");
        final int permStatus = context.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE);
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            Log.d("TEST", "Have permission");
            setSystemAnimationsScale(0.0f);
        } else {
            Log.d("TEST", "Don't have permission");
        }
    }

    void enableAnimations(final Context context) {
        final int permStatus = context.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE);
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            setSystemAnimationsScale(1.0f);
        }
    }

    private void setSystemAnimationsScale(final float animationScale) {
        try {
            final Class<?> windowManagerStubClazz = Class.forName("android.view.IWindowManager$Stub");
            final Method asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder.class);
            final Class<?> serviceManagerClazz = Class.forName("android.os.ServiceManager");
            final Method getService = serviceManagerClazz.getDeclaredMethod("getService", String.class);
            final Class<?> windowManagerClazz = Class.forName("android.view.IWindowManager");
            final Method setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales", float[].class);
            final Method getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales");

            final IBinder windowManagerBinder = (IBinder) getService.invoke(null, "window");
            final Object windowManagerObj = asInterface.invoke(null, windowManagerBinder);
            final float[] currentScales = (float[]) getAnimationScales.invoke(windowManagerObj);
            for (int i = 0; i < currentScales.length; i++) {
                Log.d("TEST", currentScales[i] + "");
                currentScales[i] = animationScale;
            }
            setAnimationScales.invoke(windowManagerObj, new Object[]{currentScales});
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}

