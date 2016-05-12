package com.visitormanagementsystem.utils;

import android.app.Application;
import android.content.ContextWrapper;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Locale;

/**
 * Created by Phani.Gullapalli on 07/12/2015.
 */
public class MyApplication extends Application {
    public static String mylanguage;


    @Override
    public void onCreate() {
        super.onCreate();
        //  Fabric.with(this, new Crashlytics());
        // Initialize the Prefs class

        Locale locale = getResources().getConfiguration().locale;
        mylanguage = locale.getLanguage();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        //  throw new RuntimeException("This is a crash");
       /* Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Alert", "Lets See if it Works !!!" + paramThrowable);
                Crashlytics.logException(paramThrowable);
            }
        });*/
        Iconify.with(new FontAwesomeModule());


    }


}
