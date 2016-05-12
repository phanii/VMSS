package com.visitormanagementsystem.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Phani.Gullapalli on 23/12/2015.
 */
public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
    Activity activity;

    public ExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));

        StringBuilder errorReport = new StringBuilder();
        errorReport.append(stringWriter.toString());
        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append("\n");
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append("\n");
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);

        Intent intent = new Intent(activity, CrashActivity.class);
        intent.putExtra("error", errorReport.toString());
        activity.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
