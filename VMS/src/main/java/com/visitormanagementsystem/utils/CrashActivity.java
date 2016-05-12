package com.visitormanagementsystem.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.visitormanagementsystem.R;

/**
 * Created by Phani.Gullapalli on 23/12/2015.
 */
public class CrashActivity extends Activity implements View.OnClickListener {
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.crashinformationreport);
        TextView textView = (TextView) findViewById(R.id.errortextinformation);
        VMSConstants.mysysout("Error " + getIntent().getStringExtra("error"));
        textView.setText(getIntent().getStringExtra("error"));
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_email);

        floatingActionButton.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        VMSConstants.mylog("back presed from error");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_email:
                MyCustomeToast.show(this, "Error sent ", true, true);
                String[] TO = {""};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Crash Report");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("error").toString());

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
