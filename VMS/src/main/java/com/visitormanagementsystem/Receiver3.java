package com.visitormanagementsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.visitormanagementsystem.utils.VMSConstants;

/**
 * Created by Phani.Gullapalli on 21/12/2015.
 */
public class Receiver3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        VMSConstants.mylog("Receiver3");
        setResultData("My Fate still continuous...");
    }
}
