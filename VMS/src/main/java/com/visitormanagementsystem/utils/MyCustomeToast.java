package com.visitormanagementsystem.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.visitormanagementsystem.R;

/**
 * Created by Phani.Gullapalli on 17/12/2015.
 */
public class MyCustomeToast {


    public static void show(Context context, String toastmsg, boolean islong, boolean mood) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_toast, null);
        TextView toast_image = (TextView) view.findViewById(R.id.toast_image);
        TextView toast_text = (TextView) view.findViewById(R.id.toast_text);
        if (mood == true) {
            toast_image.setText(R.string.toast_yes);
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } else {
            view.setBackgroundColor(Color.RED);

            toast_image.setText(R.string.toast_no);
        }
        toast_text.setText(toastmsg);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration((islong) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
