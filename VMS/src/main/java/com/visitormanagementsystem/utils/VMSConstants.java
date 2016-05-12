package com.visitormanagementsystem.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.VMSMainActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Phani.Gullapalli on 02/12/2015.
 */
public class VMSConstants {

    public static final String PROJNAME = "VMS";
    /*
    RecurrenceType
     */
    public static final int Daily = 101;
    public static final int Weekly = 102;
    public static final int Monthly = 103;
    public static final int Yearly = 104;
    /*
    WeekDay
     */
    public static final int Sunday = 101;
    public static final int Monday = 102;
    public static final int Tuesday = 103;
    public static final int Wednesday = 104;
    public static final int Thursday = 105;
    public static final int Friday = 106;
    public static final int Saturday = 107;
    /*
    MonthlyWeeks
     */
    public static final int first = 101;
    public static final int second = 102;
    public static final int third = 103;
    public static final int fourth = 104;
    public static final int last = 105;


    /*
    YearlyOptions
     */
    public static final int January = 101;
    public static final int February = 102;
    public static final int March = 103;
    public static final int April = 104;
    public static final int May = 105;
    public static final int June = 106;
    public static final int July = 107;
    public static final int August = 108;
    public static final int September = 109;
    public static final int October = 110;
    public static final int November = 111;
    public static final int December = 112;


    /*
    Gender
     */
    public static final int Male = 101;
    public static final int Female = 102;

    public static final int Save = 101;
    public static final int Submitted = 102;
    public static final int FirstLevelAppointmentApprove = 103;
    public static final int FirstLevelAppointmentReject = 104;
    public static final int SecurityApprove = 105;
    public static final int SecurityReject = 106;
    public static final int Rejected = 107;
    public static final int Cancelled = 108;
    public static final int AttendedAppointment = 109;
    public static final int AdminCancelled = 110;
    public static final int Expired = 111;
    public static final int CheckInValidationApprove = 112;
    public static final int CheckInValidationReject = 113;
    public static final int NotAttented = 114;
    public static final int Security = 115;
    public static final int Appt_reschedule = 1001;
    public static final int Appt_cancel = 1002;
    public static final int Appt_reject = 1003;

    /*
    for sysout
     */
    public static void mysysout(String s) {
        System.out.println(s);
    }

    /*
    for log
     */
    public static void mylog(String s) {

        Log.i(PROJNAME, s);
    }

    /*
    For snackbar
     */
    public static void ShowSnackbar(View v, String s) {
        Snackbar snackbar = Snackbar.make(v, s, Snackbar.LENGTH_LONG);

        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    /*
    converting file to base64 string
     */
    public static String convertFileToBase64String(String pathOnSdCard) {


        String strFile = null;

        File file = new File(pathOnSdCard);

        try {

            byte[] data = FileUtils.readFileToByteArray(file);//Convert any file, image or video into byte array
            VMSConstants.mysysout("convertFileToString data[]  " + data);

            strFile = Base64.encodeToString(data, Base64.NO_WRAP);//Convert byte array into string
            VMSConstants.mysysout("convertFileToString strFile " + strFile);
            /*
            converting Base64 string to pdf file
             */

            byte[] imageAsBytes = Base64.decode(strFile.getBytes(), Base64.DEFAULT);

            File filePath = new File(Environment.getExternalStorageDirectory() + "/braodcasts.pdf");
            FileOutputStream os = new FileOutputStream(filePath, true);
            os.write(imageAsBytes);
            os.flush();
            os.close();
            /**
             * close here
             */
            VMSConstants.mysysout("convertFileToString  decode " + new String(imageAsBytes));

        } catch (IOException e) {

            e.printStackTrace();

        }

        return strFile;

    }

    public static void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(context, VMSMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Prefs.clear();
        context.startActivity(intent);

    }

}
