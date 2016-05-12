package com.visitormanagementsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class OfficialFragment extends Fragment implements OnClickListener {
    ImageView sliderpanel_image, back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.officialfragment, null);


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
        } else if (v.equals(back)) {
            getActivity().getFragmentManager().popBackStack();
        }
    }

}
