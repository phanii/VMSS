package com.visitormanagementsystem;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.visitormanagementsystem.appointments.CompletedAppointments;
import com.visitormanagementsystem.appointments.ViewAppointmentHostory;

public class ViewAppointment extends Fragment implements OnClickListener {

    public static int[] prgmImages = {R.drawable.ft_logo2, R.drawable.ft_logo2, R.drawable.ft_logo2, R.drawable.ft_logo2,
            R.drawable.ft_logo2, R.drawable.ft_logo2, R.drawable.ft_logo2, R.drawable.ft_logo2, R.drawable.ft_logo2};
    public static String[] prgmNameList = {"Nishanth Kumar Kedala", "Nishanth Kumar Kedala", "Akhil Kumar", "Nishanth Kumar", "Nishanth", "Nishanth", "Nishanth",
            "Nishanth", "Nishanth"};
    public static String[] appointmentIds = {"Appointment Id: 57d6f62e", "Appointment Id: c92e8133", "Appointment Id: 5be0818e", "Appointment Id: 57d6f62e", "Appointment Id: c92e8133", "Appointment Id: 5be0818e", "Appointment Id: 57d6f62e",
            "Appointment Id: c92e8133", "Appointment Id: 5be0818e"};
    public static String[] mobileNumList = {"Visit Date: 20 Oct 2015", "Visit Date: 13 Oct 2015", "Visit Date: 16 Oct 2015", "Visit Date: 17 Oct 2015", "Visit Date: 12 Oct 2015 ", "Visit Date: 11 Oct 2015", "Visit Date: 13 Oct 2015",
            "Visit Date: 13 Oct 2015", "Visit Date: 13 Oct 2015"};
    static TextView tvAppointmentTypes;
    ListView lv;
    Fragment imr;
    ImageView sliderpanel_image, back;
    String service_name, strVisitorId;
    //VMSUtilities iu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.view_appointment, null);

        tvAppointmentTypes = (TextView) view.findViewById(R.id.appointmentTypes);

        Bundle bundle = getArguments();
        service_name = bundle.getString("servicename").toString();
        //textViewChanges(1);

        lv = (ListView) view.findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, prgmNameList, appointmentIds, mobileNumList, prgmImages));
        sliderpanel_image = (ImageView) view.findViewById(R.id.sliderpanel_image);
        back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(this);
        sliderpanel_image.setOnClickListener(this);

        tvAppointmentTypes.setText(service_name);
        //iu = new VMSUtilities(getActivity());

        doinBackground();

        return view;
    }

    private void doinBackground() {


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.equals(sliderpanel_image)) {
            ServicesFragment.mSlidingPanel.openPane();
        } else if (v.equals(back)) {
            imr = new EmployeeDashboard();
            if (imr != null) {
                FragmentManager fmr = getFragmentManager();
                fmr.beginTransaction().replace(R.id.frame_container1, imr).commit();
            }
        }
    }

    public class CustomAdapter extends BaseAdapter {
        String[] result;
        String[] mobileNumList_;
        String[] appointIds;
        int[] imageId;
        private LayoutInflater inflater = null;

        public CustomAdapter(ViewAppointment mainActivity, String[] prgmNameList, String[] apppointmentIds, String[] mobileNumList, int[] prgmImages) {
            // TODO Auto-generated constructor stub
            result = prgmNameList;
            imageId = prgmImages;
            mobileNumList_ = mobileNumList;
            appointIds = apppointmentIds;
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return result.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder = new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.view_appointment_listitem, null);
            holder.tv = (TextView) rowView.findViewById(R.id.textView1);
            holder.tv_appoint_id = (TextView) rowView.findViewById(R.id.textView2);
            holder.tv_num = (TextView) rowView.findViewById(R.id.mobileNum);
//			holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
            holder.tv.setText(result[position]);

            holder.tv_appoint_id.setText(appointIds[position]);

            holder.tv_num.setText(mobileNumList_[position]);
//			holder.img.setImageResource(imageId[position]);
            rowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //	Toast.makeText(getActivity(), "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                    if (service_name.equals("Approved")) {
                        imr = new ViewAppointmentHostory();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        }
                    } else if (service_name.equals("To Be Approved")) {
                        imr = new ApproveOrRejectByEmploy();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        }
                    } else if (service_name.equals("Rejected")) {
                        imr = new RejectedAppointments();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        }
                    } else if (service_name.equals("Completed")) {
                        imr = new CompletedAppointments();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        }
                    } else {
                        imr = new MyAppointments();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        }
                    }

                }
            });
            return rowView;
        }

        public class Holder {
            TextView tv, tv_num, tv_appoint_id;
//			ImageView img;
        }
    }
}
