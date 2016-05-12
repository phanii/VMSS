package com.visitormanagementsystem;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.visitormanagementsystem.schedule.ScheduleAppointment;

public class AppointmentTypes extends Fragment implements OnClickListener {

    ViewGroup view;
    TextView tvScheduleAppointment, tvViewAppointment;
 Fragment imr;
    ImageView sliderpanel_image;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.appointment_types, null);

        tvScheduleAppointment = (TextView) view.findViewById(R.id.scheduleAppointment);
        tvViewAppointment = (TextView) view.findViewById(R.id.viewAppointment);

        tvScheduleAppointment.setOnClickListener(this);
        tvViewAppointment.setOnClickListener(this);

        sliderpanel_image = (ImageView) view.findViewById(R.id.sliderpanel_image);
        sliderpanel_image.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v.equals(tvScheduleAppointment)) {
            imr = new ScheduleAppointment();
            if (imr != null) {
                FragmentManager fmr = getFragmentManager();

                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
            }
        } else if (v.equals(tvViewAppointment)) {
            imr = new ScheduleAppointment();
            if (imr != null) {
                FragmentManager fmr = getFragmentManager();
                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
            }
        } else if (v.equals(sliderpanel_image)) {
            if(ServicesFragment.mSlidingPanel.isOpen())
            {
                ServicesFragment.mSlidingPanel.closePane();
            }
            else
            {
            ServicesFragment.mSlidingPanel.openPane();

            }

        }

    }
}