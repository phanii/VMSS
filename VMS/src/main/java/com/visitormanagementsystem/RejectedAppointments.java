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

public class RejectedAppointments extends Fragment implements OnClickListener {
    ImageView sliderpanel_image, back;
    TextView tvGoBack;
    Fragment imr;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.rejected_appoinrments, null);

        tvGoBack = (TextView) view.findViewById(R.id.goBack);
        tvGoBack.setOnClickListener(this);

        bundle = new Bundle();
        back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(this);


        sliderpanel_image = (ImageView) view.findViewById(R.id.sliderpanel_image);
        sliderpanel_image.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.equals(sliderpanel_image)) {
            ServicesFragment.mSlidingPanel.openPane();
        } else if (v.equals(tvGoBack)) {
            imr = new ViewAppointment();
            if (imr != null) {
                FragmentManager fmr = getFragmentManager();
                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                bundle.putString("servicename", "Rejected");
                imr.setArguments(bundle);
            }
        } else if (v.equals(back)) {
            getActivity().getFragmentManager().popBackStack();
        }
    }
}
