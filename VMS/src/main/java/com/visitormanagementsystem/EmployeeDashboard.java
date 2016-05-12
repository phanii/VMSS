package com.visitormanagementsystem;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.adapters.Recy_SingleText_EmpDashboardAdapter;
import com.visitormanagementsystem.appointments.CompleteListOfAppointments;
import com.visitormanagementsystem.pojos.EmployeeDashBoardQuickActionsInformationPojo;
import com.visitormanagementsystem.utils.SimpleDividerItemDecoration;
import com.visitormanagementsystem.utils.VMSConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class EmployeeDashboard extends Fragment implements OnClickListener {
    //TextView tvCompleted,tvApproved,tvTobeApproved,tvRejected;
    public static int[] prgmImages = {R.drawable.completed, R.drawable.approved, R.drawable.tobeapproved, R.drawable.rejected};
    public static String[] prgmNameList = {"Completed", "Approved", "To Be Approved", "Rejected"};

    ListView lv;
    Context context;
    android.app.Fragment imr;
    Bundle bundle;
    SharedPreferences pref;
    @Bind(R.id.epdashboard_recy_listView)
    RecyclerView epdashboard_recy_listView;
    String userrole;
    boolean isMyAppointment = true;
    @Bind(R.id.spinner_empdashboard_actions)
    Spinner spinner_empdashboard_actions;
    @Bind(R.id.spinner_empdashboard_actions_rl)
    RelativeLayout spinner_empdashboard_actions_rl;
    ProgressDialog progressDialog;

    //	String strType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.employee_dashboard, null);
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView abTitle = (TextView) view.findViewById(titleId);

        ButterKnife.bind(this, view);


        userrole = Prefs.getString("userRoleId", "");
        getActivity().setTitle(getActivity().getString(R.string.dashboard));


        Spannable text = new SpannableString(getActivity().getTitle());
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);


        lv = (ListView) view.findViewById(R.id.listView);
//        lv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages));
        System.out.println("userRoleId  " + Prefs.getString("userRoleId", ""));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (userrole.equalsIgnoreCase("5")) {
            spinner_empdashboard_actions_rl.setVisibility(View.GONE);

        }


//		 pref = getActivity().getSharedPreferences("MyPREFERENCES", getActivity().MODE_PRIVATE);
//		 strType = pref.getString("type", null); 

        bundle = new Bundle();

        return view;
    }

    @OnItemSelected(R.id.spinner_empdashboard_actions)
    void onItemSelected(int position) {
        VMSConstants.mysysout(position + " Selected position " + spinner_empdashboard_actions.getItemAtPosition(position));
        if (position == 1) {
            /**
             *
             */
            isMyAppointment = false;
            serviceRequest();

        } else {
            isMyAppointment = true;
            serviceRequest();
        }


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (userrole.equalsIgnoreCase("5")) {
            isMyAppointment = true;

        }

        // abTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        serviceRequest();

    }


    public void serviceRequest() {
        final List<EmployeeDashBoardQuickActionsInformationPojo> employeeDashBoardQuickActionsInformationPojoslist = new ArrayList<>();
/**
 * Using dummy data
 */
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("1", "1", "Today Visitiors", 2));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("2", "2", "Approves", 3));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("3", "3", "Not Approve", 2));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("4", "4", "Save", 2));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("5", "5", "In Progress", 2));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("6", "6", "To be Approved", 2));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("7", "7", "Attended", 2));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("8", "8", "Cancelled", 2));
        employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo("3", "3", "Not Approve", 2));






        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        epdashboard_recy_listView.setLayoutManager(llm);
        epdashboard_recy_listView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        // System.out.println("employeeDashBoardQuickActionsInformationPojoslist  " + employeeDashBoardQuickActionsInformationPojoslist.size());
        epdashboard_recy_listView.setAdapter(new Recy_SingleText_EmpDashboardAdapter(employeeDashBoardQuickActionsInformationPojoslist));

        progressDialog.dismiss();
       /*

        PostClient postClient = new PostClient();

        Call<List<EmployeeDashBoardQuickActionsInformationPojo>> listCall = postClient.myApiEndpointInterface.getEmployeeDashBoardQuickActionsInformationPojoCall(Prefs.getString("UserId", ""),
                userrole, Prefs.getString("userName", ""), "", "", "", "", isMyAppointment);

        listCall.enqueue(new Callback<List<EmployeeDashBoardQuickActionsInformationPojo>>() {
            @Override
            public void onResponse(Response<List<EmployeeDashBoardQuickActionsInformationPojo>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    System.out.println(getClass().getName() + "\n" + response.raw());

                    List<EmployeeDashBoardQuickActionsInformationPojo> employeeDashBoardQuickActionsInformationPojos = response.body();

                    try {
                        if (employeeDashBoardQuickActionsInformationPojos.size() > 0) {


                            for (int i = 0; i < employeeDashBoardQuickActionsInformationPojos.size(); i++) {
                                employeeDashBoardQuickActionsInformationPojoslist.add(new EmployeeDashBoardQuickActionsInformationPojo(employeeDashBoardQuickActionsInformationPojos.get(i).get$id(), employeeDashBoardQuickActionsInformationPojos.get(i).getActionCode(),
                                        employeeDashBoardQuickActionsInformationPojos.get(i).getActionName(), employeeDashBoardQuickActionsInformationPojos.get(i).getActionCount()));
                            }
                            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            epdashboard_recy_listView.setLayoutManager(llm);
                            epdashboard_recy_listView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                            // System.out.println("employeeDashBoardQuickActionsInformationPojoslist  " + employeeDashBoardQuickActionsInformationPojoslist.size());
                            epdashboard_recy_listView.setAdapter(new Recy_SingleText_EmpDashboardAdapter(employeeDashBoardQuickActionsInformationPojoslist));

                            // lv.setAdapter(new CustomAdapter(this, employeeDashBoardQuickActionsInformationPojoslist, prgmImages));
                            // lv.setAdapter(new CustomAdapter(employeeDashBoardQuickActionsInformationPojos));
                        } else {
                            MyCustomeToast.show(getActivity(), getActivity().getString(R.string.nodatafoundtodisplay), true, false);
                        }
                    } catch (Exception e) {

                        VMSConstants.mysysout("Error " + e.getMessage());
                        try {
                            MyCustomeToast.show(getActivity(), getActivity().getString(R.string.nodatafoundtodisplay), true, false);

                        } catch (Exception e2) {
                            VMSConstants.mysysout("Error2 " + e.getMessage());
                        }
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    MyCustomeToast.show(getActivity(), getActivity().getString(R.string.tryagain), true, false);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                MyCustomeToast.show(getActivity(), getActivity().getString(R.string.tryagain), true, false);
                VMSConstants.mylog("failed " + t);

            }
        });
        */

    }
    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e("gif--", "fragment back key is clicked");

                    return true;
                }
                return false;
            }
        });
    }*/

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    public class CustomAdapter extends BaseAdapter {
        String[] result;
        int[] imageId;
        List<EmployeeDashBoardQuickActionsInformationPojo> list_EmployeeDashBoardQuickActionsInformationPojos;
        private LayoutInflater inflater = null;

        public CustomAdapter(EmployeeDashboard employeeDashboard, String[] prgmNameList, int[] prgmImages) {
            // TODO Auto-generated constructor stub
            result = prgmNameList;
            imageId = prgmImages;
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public CustomAdapter(List<EmployeeDashBoardQuickActionsInformationPojo> list_EmployeeDashBoardQuickActionsInformationPojos) {
            this.list_EmployeeDashBoardQuickActionsInformationPojos = list_EmployeeDashBoardQuickActionsInformationPojos;
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list_EmployeeDashBoardQuickActionsInformationPojos.size();
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
            rowView = inflater.inflate(R.layout.employee_dashboard_listitem, null);

            holder.tv = (TextView) rowView.findViewById(R.id.textView1);
            holder.img = (ImageView) rowView.findViewById(R.id.imageView);

            holder.tv.setText(list_EmployeeDashBoardQuickActionsInformationPojos.get(position).getActionName() + "(" + list_EmployeeDashBoardQuickActionsInformationPojos.get(position).getActionCount() + ")");
            //holder.img.setImageResource(imageId[position]);


//			holder.img.setImageResource(imageId[position]);
            rowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //	Toast.makeText(getActivity(), "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                    if (position == 0) {
                        imr = new CompleteListOfAppointments();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                            bundle.putString("servicename", "Completed");
                            imr.setArguments(bundle);

                        }
                    } else if (position == 1) {
                        imr = new ViewAppointment();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                            bundle.putString("servicename", "Approved");
                            imr.setArguments(bundle);
                        }
                    } else if (position == 2) {
                        imr = new ViewAppointment();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                            bundle.putString("servicename", "To Be Approved");
                            imr.setArguments(bundle);
                        }
                    } else if (position == 3) {
                        imr = new ViewAppointment();
                        if (imr != null) {
                            FragmentManager fmr = getFragmentManager();
                            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                            bundle.putString("servicename", "Rejected");
                            imr.setArguments(bundle);
                        }
                    }


                }
            });
            return rowView;
        }

        public class Holder {
            TextView tv;
            ImageView img;
        }
    }
}
