package com.visitormanagementsystem;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.appointments.CompleteListOfAppointments;
import com.visitormanagementsystem.schedule.ScheduleAppointment;
import com.visitormanagementsystem.user_registration.RegisterNewUser;
import com.visitormanagementsystem.userprofile_related.ActivateYourAccount;
import com.visitormanagementsystem.userprofile_related.ChangePassword;
import com.visitormanagementsystem.userprofile_related.ForgotPassword;
import com.visitormanagementsystem.userprofile_related.MyProfile;
import com.visitormanagementsystem.utils.VMSConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.mingle.widget.ShapeLoadingDialog;

public class ServicesFragment extends FragmentActivity {

    public static SlidingPaneLayout mSlidingPanel;
    //10.0.2.2 is the address used by the Android emulators to refer to the host address
    // change this to the IP of another host if required
    private static String ageURL = "http://172.16.100.244:9090/api/Appointment/885538A1-B99C-4C31-AC85-053D774959DC";
    private static String getAge = "getAge";
    private static String jsonResult = "success";
    FrameLayout f1;
    String[] MenuTitles;
    int appt = R.drawable.appointment;
    Drawable[] slider_array;
    ListView services_listview;
    Button servicefragment_home_btn;
    Fragment imr;
    Intent intent;
    String service;
    Bundle bundle;
    //VMSUtilities iu;
    ProgressDialog progressDialog;
    String strVisitorName, strEmployeeId, strCompanyName, strDesignation, strSectorCompCategory;
    @Bind(R.id.sidemenu_profilename)
    TextView sidemenu_profilename;
    //  private ShapeLoadingDialog shapeLoadingDialog;


    @Bind(R.id.userprofileImage)
    ImageView userprofileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicesfragment);
        ButterKnife.bind(this);
     /*   shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("Loading...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(this.getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);


        f1 = (FrameLayout) findViewById(R.id.frame_container1);

        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);

       // mSlidingPanel.setPanelSlideListener(new PaneListener());

        services_listview = (ListView) findViewById(R.id.services_listview);
        servicefragment_home_btn = (Button) findViewById(R.id.servicefragment_home_btn);

        bundle = new Bundle();




        sidemenu_profilename.setText(Prefs.getString("userName", ""));
        intent = getIntent();
        service = intent.getExtras().getString("service");
        /*
        user
         */

        if (Prefs.getString("nav_userpic", "").trim().length() > 0) {
            VMSConstants.mysysout("nav pic adjusted");
            String photostring = Prefs.getString("nav_userpic", "");

            byte[] imageAsBytes = Base64.decode(photostring.getBytes(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

            userprofileImage.setImageBitmap(bitmap);
        }
        /*
        user
         */
        if (Prefs.getString("userRoleId", "").equalsIgnoreCase("5")) {
            MenuTitles = new String[]{"Home", "Appointment", "Registration", "My Profile",
                    "Dashboard",
                    "Change Password", "QR", "Log off",
            };
            slider_array = new Drawable[]
                    {new IconDrawable(this, FontAwesomeIcons.fa_home), new IconDrawable(this, FontAwesomeIcons.fa_calendar),
                            new IconDrawable(this, FontAwesomeIcons.fa_pencil_square_o), new IconDrawable(this, FontAwesomeIcons.fa_user),
                            new IconDrawable(this, FontAwesomeIcons.fa_dashboard),
                            new IconDrawable(this, FontAwesomeIcons.fa_eye), new IconDrawable(this, FontAwesomeIcons.fa_qrcode),
                            new IconDrawable(this, FontAwesomeIcons.fa_power_off)};
        }

        /*
        Security
         */
        else if (Prefs.getString("userRoleId", "").equalsIgnoreCase("2")) {
            MenuTitles = new String[]{"Home", "Appointment",
                    "Registration", "My Profile",
                    "Dashboard", "Security",
                    "Change Password", "Log off"};
            slider_array = new Drawable[]
                    {
                            new IconDrawable(this, FontAwesomeIcons.fa_home), new IconDrawable(this, FontAwesomeIcons.fa_calendar),
                            new IconDrawable(this, FontAwesomeIcons.fa_pencil_square_o), new IconDrawable(this, FontAwesomeIcons.fa_user),
                            new IconDrawable(this, FontAwesomeIcons.fa_dashboard), new IconDrawable(this, FontAwesomeIcons.fa_user_secret),
                            new IconDrawable(this, FontAwesomeIcons.fa_eye), new IconDrawable(this, FontAwesomeIcons.fa_power_off)};
        }
/*
Employee
 */
        else if (Prefs.getString("userRoleId", "").equalsIgnoreCase("4")) {
            MenuTitles = new String[]
                    {"Home", "Appointment",
                            "Registration", "My Profile",
                            "Dashboard",
                            "Change Password", "Log off"};
            slider_array = new Drawable[]
                    {
                            new IconDrawable(this, FontAwesomeIcons.fa_home), new IconDrawable(this, FontAwesomeIcons.fa_calendar),
                            new IconDrawable(this, FontAwesomeIcons.fa_pencil_square_o), new IconDrawable(this, FontAwesomeIcons.fa_user),
                            new IconDrawable(this, FontAwesomeIcons.fa_dashboard),
                            new IconDrawable(this, FontAwesomeIcons.fa_eye), new IconDrawable(this, FontAwesomeIcons.fa_power_off)};
        }

/*
Admin
 */
        else if (Prefs.getString("userRoleId", "").equalsIgnoreCase("1")) {
            MenuTitles = new String[]{
                    "Home", "Appointment",
                    "Registration", "My Profile",
                    "Dashboard",
                    "Change Password", "Log off"};
            slider_array = new Drawable[]
                    {
                            new IconDrawable(this, FontAwesomeIcons.fa_home), new IconDrawable(this, FontAwesomeIcons.fa_calendar),
                            new IconDrawable(this, FontAwesomeIcons.fa_pencil_square_o), new IconDrawable(this, FontAwesomeIcons.fa_user),
                            new IconDrawable(this, FontAwesomeIcons.fa_dashboard),
                            new IconDrawable(this, FontAwesomeIcons.fa_eye), new IconDrawable(this, FontAwesomeIcons.fa_power_off)};
        }
/*
SuperAdmin
 */
        else if (Prefs.getString("userRoleId", "").equalsIgnoreCase("3")) {
            MenuTitles = new String[]{
                    "Home", "Appointment",
                    "Registration", "My Profile",
                    "Dashboard", "Security",
                    "Administration",
                    "Change Password", "Log off"};
            slider_array = new Drawable[]
                    {
                            new IconDrawable(this, FontAwesomeIcons.fa_home), new IconDrawable(this, FontAwesomeIcons.fa_calendar),
                            new IconDrawable(this, FontAwesomeIcons.fa_pencil_square_o), new IconDrawable(this, FontAwesomeIcons.fa_user),
                            new IconDrawable(this, FontAwesomeIcons.fa_dashboard), new IconDrawable(this, FontAwesomeIcons.fa_user_secret),
                            new IconDrawable(this, FontAwesomeIcons.fa_cogs),
                            new IconDrawable(this, FontAwesomeIcons.fa_eye), new IconDrawable(this, FontAwesomeIcons.fa_power_off)};
        } else {
            MenuTitles = new String[]{
                    "Home", "Appointment",
                    "Registration", "My Profile",
                    "Dashboard", "Security",
                    "Administration",
                    "Change Password", "Log off"};
            slider_array = new Drawable[]
                    {

                            new IconDrawable(this, FontAwesomeIcons.fa_home), new IconDrawable(this, FontAwesomeIcons.fa_calendar),
                            new IconDrawable(this, FontAwesomeIcons.fa_pencil_square_o), new IconDrawable(this, FontAwesomeIcons.fa_user),
                            new IconDrawable(this, FontAwesomeIcons.fa_dashboard), new IconDrawable(this, FontAwesomeIcons.fa_user_secret),
                            new IconDrawable(this, FontAwesomeIcons.fa_cogs),
                            new IconDrawable(this, FontAwesomeIcons.fa_eye), new IconDrawable(this, FontAwesomeIcons.fa_power_off)};
        }


      //  services_listview.setAdapter(new CustomAdapter(getApplicationContext(), MenuTitles, slider_array));

        //	services_listview.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, MenuTitles));
        // services_listview.setSelector(R.drawable.listselector);
        services_listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
/**
 * change the menu item bgcolor
 */
                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(ContextCompat.getColor(ServicesFragment.this, R.color.white));
                    view.setBackgroundColor(ContextCompat.getColor(ServicesFragment.this, R.color.stricolor_light));
                }


                System.out.println("po " + MenuTitles[((int) parent.getAdapter().getItem(position))]);
                if (position == 0) {

                    Intent intent = new Intent(ServicesFragment.this, VMSMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "Appointment") {
                    System.out.println("inside apt");

                    final Dialog dialog = new Dialog(ServicesFragment.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_alert_dialog);

                    //dialog.setTitle("Appointment Types");

                    TextView tvViewAppointment = (TextView) dialog.findViewById(R.id.dialogViewAppointment);
                    TextView tvScheduleAppointment = (TextView) dialog.findViewById(R.id.dialogScheduleAppointment);

                    tvViewAppointment.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            imr = new CompleteListOfAppointments();
                            if (imr != null) {
                                FragmentManager fmr = getFragmentManager();
                                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                                bundle.putString("servicename", "My Appointments");
                                imr.setArguments(bundle);
                                mSlidingPanel.closePane();
                                services_listview.setEnabled(false);
                            }
                        }
                    });
                    tvScheduleAppointment.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            imr = new ScheduleAppointment();
                            if (imr != null) {
                                FragmentManager fmr = getFragmentManager();
                                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                                mSlidingPanel.closePane();
                                services_listview.setEnabled(false);
                            }
                        }
                    });

                    TextView dialogButton = (TextView) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "Registration") {
                    imr = new RegisterNewUser();
                    if (imr != null) {
                        FragmentManager fmr = getFragmentManager();
                        fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        mSlidingPanel.closePane();
                        services_listview.setEnabled(false);
                    }
                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "My Profile") {
                    imr = new MyProfile();
                    if (imr != null) {
                        FragmentManager fmr = getFragmentManager();
                        fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        mSlidingPanel.closePane();
                        services_listview.setEnabled(false);
                    }
                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "Dashboard") {
                    imr = new EmployeeDashboard();
                    if (imr != null) {
                        FragmentManager fmr = getFragmentManager();
                        fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        mSlidingPanel.closePane();
                        services_listview.setEnabled(false);
                    }
                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "Security") {

                    final Dialog dialog = new Dialog(ServicesFragment.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.security_custom_alert_dialog);
                    dialog.setTitle("Appointment Types");

                    TextView tvValidateAppointment = (TextView) dialog.findViewById(R.id.validateAppointment);
                    TextView tvGatePass = (TextView) dialog.findViewById(R.id.gatePass);
                    TextView tvOnBehalf = (TextView) dialog.findViewById(R.id.onBehalfAppointment);

                    tvValidateAppointment.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            imr = new ApproveOrRejectByEmploy();
                            if (imr != null) {
                                FragmentManager fmr = getFragmentManager();
                                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                                mSlidingPanel.closePane();
                                services_listview.setEnabled(false);
                            }
                        }
                    });
                    tvGatePass.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            imr = new ScheduleAppointment();
                            if (imr != null) {
                                FragmentManager fmr = getFragmentManager();
                                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                                mSlidingPanel.closePane();
                                services_listview.setEnabled(false);
                            }
                        }
                    });
                    tvOnBehalf.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            imr = new ScheduleAppointment();
                            if (imr != null) {
                                FragmentManager fmr = getFragmentManager();
                                fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                                mSlidingPanel.closePane();
                                services_listview.setEnabled(false);
                            }
                        }
                    });

                    TextView dialogButton = (TextView) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "Administration") {

//

                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "Change Password") {

                    imr = new ChangePassword();
                    if (imr != null) {
                        FragmentManager fmr = getFragmentManager();
                        fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
                        mSlidingPanel.closePane();
                        services_listview.setEnabled(false);
                    }


                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "Log off") {
                    Prefs.clear();
                    Intent intent = new Intent(ServicesFragment.this, VMSMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else if (MenuTitles[((int) parent.getAdapter().getItem(position))] == "QR") {
                    Prefs.clear();
                    Intent intent = new Intent(ServicesFragment.this, QRReader.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {

                }
            }

        });

        if (service.equalsIgnoreCase("forgotpassword")) {
            // imr = new OfficialFragment();
            imr = new ForgotPassword();

        } else if (service.equalsIgnoreCase("registernewuser")) {
            imr = new RegisterNewUser();
        } else if (service.equalsIgnoreCase("activateyouraccount")) {
            imr = new ActivateYourAccount();
        } else if (service.equalsIgnoreCase("scheduleappointment")) {
            imr = new ScheduleAppointment();
        } else if (service.equalsIgnoreCase("login")) {
            imr = new EmployeeDashboard();
        }

        if (imr != null) {

            FragmentManager fmr = getFragmentManager();

            fmr.beginTransaction().replace(R.id.frame_container1, imr).addToBackStack(null).commit();
            services_listview.setEnabled(false);
        }

    }

    @Override
    public void onBackPressed() {
        /**
         * Back Disabled here ...
         */

    }


    public class CustomAdapter extends BaseAdapter {
        String[] result;
        Drawable[] slider_images;

        private LayoutInflater inflater = null;

        public CustomAdapter(Context context, String[] prgmNameList, Drawable[] drawable_array) {
            // TODO Auto-generated constructor stub
            result = prgmNameList;
            slider_images = drawable_array;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            rowView = inflater.inflate(R.layout.custom_slide_listitem, null);

            holder.tv = (TextView) rowView.findViewById(R.id.textView1);
            holder.tv.setText(result[position]);

            holder.ivSlider = (ImageView) rowView.findViewById(R.id.imageView_slider);
            holder.ivSlider.setImageDrawable(slider_array[position]);


			/*rowView.setOnClickListener(new OnClickListener() {
                @Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				//	Toast.makeText(getActivity(), "You Clicked " + result[position], Toast.LENGTH_LONG).show();
					imr = new ViewAppointmentHostory();
					if (imr != null) {
						FragmentManager fmr = getFragmentManager();
						fmr.beginTransaction().replace(R.id.frame_container, imr).commit();
					//	mSlidingPanel.closePane();
					}


				}
			});*/
            return rowView;
        }

        public class Holder {
            TextView tv, tv_num, tv_appoint_id;
            ImageView ivSlider;
        }
    }


    private class PaneListener implements android.support.v4.widget.SlidingPaneLayout.PanelSlideListener {

        @Override
        public void onPanelClosed(View view) {

            services_listview.setEnabled(false);
        }

        @Override
        public void onPanelOpened(View view) {

            services_listview.setEnabled(true);
        }

        @Override
        public void onPanelSlide(View view, float arg1) {

        }

    }


}
