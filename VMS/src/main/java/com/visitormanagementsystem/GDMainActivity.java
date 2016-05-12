package com.visitormanagementsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.schedule.ScheduleAppointment;
import com.visitormanagementsystem.user_registration.RegisterNewUser;
import com.visitormanagementsystem.userprofile_related.ChangePassword;
import com.visitormanagementsystem.userprofile_related.MyProfile;
import com.visitormanagementsystem.utils.VMSConstants;

import java.lang.ref.WeakReference;

import it.neokree.googlenavigationdrawer.GAccount;
import it.neokree.googlenavigationdrawer.GAccountListener;
import it.neokree.googlenavigationdrawer.GSection;
import it.neokree.googlenavigationdrawer.GSectionListener;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;

/**
 * Created by Phani.Gullapalli on 21/12/2015.
 */
public class GDMainActivity extends GoogleNavigationDrawer implements GAccountListener {
    GSection logoutsection, Appointmentsection, QRsection;
    GAccount gAccount;
    DrawerLayout layout;
    WeakReference weakReference;
    GoogleNavigationDrawer googleNavigationDrawer;

    @Override

    public void init(Bundle savedInstanceState) {
        GAccount gAccount = new GAccount("Name", "emailaddress", ContextCompat.getDrawable(this, R.drawable.profile), ContextCompat.getDrawable(this, R.drawable.vms_splash2));


        this.addAccount(gAccount);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.vms_splash);
        drawable.setAlpha(40);
        weakReference = new WeakReference(drawable);
        gAccount.setBackground(drawable);


        TextView username = (TextView) this.findViewById(it.neokree.googlenavigationdrawer.R.id.user_nome);
        TextView usermail = (TextView) this.findViewById(it.neokree.googlenavigationdrawer.R.id.user_email);
        username.setTextColor(ContextCompat.getColor(this, R.color.black));
        usermail.setTextColor(ContextCompat.getColor(this, R.color.black));
        usermail.setTextSize(10);
        this.setAccountListener(this);
        layout = (DrawerLayout) this.findViewById(it.neokree.googlenavigationdrawer.R.id.drawer_layout);

        this.addSection(this.newSection(getString(R.string.nav_dashboard), new IconDrawable(this, FontAwesomeIcons.fa_dashboard), new EmployeeDashboard()).setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor))
        );
        this.addDivisor();
        Appointmentsection = this.newSection(getString(R.string.nav_appoinment), new IconDrawable(this, FontAwesomeIcons.fa_pencil_square_o), new RegisterNewUser()).setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor));
        this.addSection(Appointmentsection);
        this.addDivisor();
        this.addSection(this.newSection(getString(R.string.nav_registration), new IconDrawable(this, FontAwesomeIcons.fa_calendar), new RegisterNewUser()).setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor)));
        this.addDivisor();
        if (Prefs.getString("userRoleId", "").equalsIgnoreCase("3")) {
            this.addSection(this.newSection(getString(R.string.nav_security), new IconDrawable(this, FontAwesomeIcons.fa_user_secret), new EmployeeDashboard()).setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor)));
            this.addDivisor();
            this.addSection(this.newSection(getString(R.string.nav_admin), new IconDrawable(this, FontAwesomeIcons.fa_cogs), new EmployeeDashboard()).setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor)));
            this.addDivisor();
        } else if (Prefs.getString("userRoleId", "").equalsIgnoreCase("2")) {
            this.addSection(this.newSection(getString(R.string.nav_security), new IconDrawable(this, FontAwesomeIcons.fa_user_secret), new EmployeeDashboard()).setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor)));
            this.addDivisor();
            QRsection = this.newSection(getString(R.string.nav_qr), new IconDrawable(this, FontAwesomeIcons.fa_qrcode), "").setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor));
            this.addSection(QRsection);
            this.addDivisor();
            QRsection.setOnClickListener(gSectionListener);

        }


        String emailaddressIs = Prefs.getString("userName", "");
        int justNameIndex = emailaddressIs.indexOf('@');
        String JustName = emailaddressIs.substring(0, justNameIndex);


        gAccount.setTitle(JustName);
        gAccount.setSubTitle(emailaddressIs);
        this.addSection(this.newSection(getString(R.string.nav_changepwd), new IconDrawable(this, FontAwesomeIcons.fa_eye), new ChangePassword()).setSectionColor(ContextCompat.getColor(this, R.color.actionbarcolor)));
        this.addDivisor();
        logoutsection = this.newSection(getString(R.string.nav_logoff), new IconDrawable(this, FontAwesomeIcons.fa_power_off), "").setSectionColor(Color.RED);

        this.addBottomSection(logoutsection);
        logoutsection.setOnClickListener(gSectionListener);
        Appointmentsection.setOnClickListener(gSectionListener);

    }


    GSectionListener gSectionListener = new GSectionListener() {
        @Override
        public void onClick(GSection section) {
            VMSConstants.mysysout("section.getTitle()  " + section.getTitle());
            switch (section.getTitle()) {
                case "إطفاء":
                    Prefs.clear();


                    final AlertDialog alertDialog = new AlertDialog.Builder(GDMainActivity.this).create();
                    alertDialog.setCancelable(false);
                    alertDialog.setTitle(getString(R.string.exit));
                    alertDialog.setMessage(getString(R.string.wanttoexit));
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GDMainActivity.this, VMSMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Prefs.clear();
                            alertDialog.dismiss();
                            weakReference.clear();
                            startActivity(intent);
                        }
                    });

                    alertDialog.show();
                    break;
                case "Log Off":
                    Prefs.clear();


                    final AlertDialog alertDialog1 = new AlertDialog.Builder(GDMainActivity.this).create();
                    alertDialog1.setCancelable(false);
                    alertDialog1.setTitle(getString(R.string.exit));
                    alertDialog1.setMessage(getString(R.string.wanttoexit));
                    alertDialog1.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog1.dismiss();
                        }
                    });
                    alertDialog1.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GDMainActivity.this, VMSMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Prefs.clear();
                            alertDialog1.dismiss();
                            weakReference.clear();
                            startActivity(intent);
                        }
                    });

                    alertDialog1.show();
                    break;

                case "موعد":
                    System.out.println("inside apt");

                    final Dialog dialog = new Dialog(GDMainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_alert_dialog);

                    //dialog.setTitle("Appointment Types");

                    TextView tvViewAppointment = (TextView) dialog.findViewById(R.id.dialogViewAppointment);
                    TextView tvScheduleAppointment = (TextView) dialog.findViewById(R.id.dialogScheduleAppointment);

                    tvViewAppointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//CompleteListOfAppointments


                            layout.closeDrawers();
                            dialog.dismiss();
                            EmployeeDashboard employeeDashboard = new EmployeeDashboard();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            //fragmentTransaction.add(completeListOfAppointments, "frag_CompleteListOfAppointments");
                            fragmentTransaction.replace(R.id.frame_container, employeeDashboard);
                            fragmentTransaction.commit();


                        }
                    });
                    tvScheduleAppointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            layout.closeDrawers();
//ScheduleAppointment
                            ScheduleAppointment scheduleAppointment = new ScheduleAppointment();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            //fragmentTransaction.add(completeListOfAppointments, "frag_CompleteListOfAppointments");
                            fragmentTransaction.replace(R.id.frame_container, scheduleAppointment);
                            fragmentTransaction.commit();
                        }
                    });

                    TextView dialogButton = (TextView) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    break;
                case "Appointment":
                    System.out.println("inside apt");

                    final Dialog dialog1 = new Dialog(GDMainActivity.this);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.custom_alert_dialog);

                    //dialog.setTitle("Appointment Types");

                    TextView tvViewAppointment1 = (TextView) dialog1.findViewById(R.id.dialogViewAppointment);
                    TextView tvScheduleAppointment1 = (TextView) dialog1.findViewById(R.id.dialogScheduleAppointment);

                    tvViewAppointment1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//CompleteListOfAppointments


                            layout.closeDrawers();
                            dialog1.dismiss();
                            EmployeeDashboard employeeDashboard = new EmployeeDashboard();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            //fragmentTransaction.add(completeListOfAppointments, "frag_CompleteListOfAppointments");
                            fragmentTransaction.replace(R.id.frame_container, employeeDashboard);
                            fragmentTransaction.commit();


                        }
                    });
                    tvScheduleAppointment1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            layout.closeDrawers();
//ScheduleAppointment
                            ScheduleAppointment scheduleAppointment = new ScheduleAppointment();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            //fragmentTransaction.add(completeListOfAppointments, "frag_CompleteListOfAppointments");
                            fragmentTransaction.replace(R.id.frame_container, scheduleAppointment);
                            fragmentTransaction.commit();
                        }
                    });

                    TextView dialogButton1 = (TextView) dialog1.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });

                    dialog1.show();
                    break;
                case "الاستجابة":
                    startActivity(new Intent(GDMainActivity.this, QRReader.class));
                    break;
                case "QR":
                    startActivity(new Intent(GDMainActivity.this, QRReader.class));
                    break;
                default:
                    break;

            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.myprofilemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        VMSConstants.mysysout("this.getFragmentManager().getBackStackEntryCount()  " + this.getFragmentManager().getBackStackEntryCount());
        switch (id) {
            case R.id.menu_back:
                if (this.getFragmentManager().getBackStackEntryCount() > 0) {

                    this.getFragmentManager().popBackStack();
                } else {
                    try {

                        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getString(R.string.exit));
                        alertDialog.setMessage(getString(R.string.wanttoexit));
                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GDMainActivity.this, VMSMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Prefs.clear();
                                alertDialog.dismiss();
                                startActivity(intent);
                            }
                        });

                        alertDialog.show();
                    } catch (Exception e) {
                        Intent intent = new Intent(GDMainActivity.this, VMSMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Prefs.clear();

                        startActivity(intent);
                    }

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Prefs.clear();
    }

    @Override
    public void onAccountOpening(GAccount account) {
        MyProfile myProfile = new MyProfile();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //fragmentTransaction.add(completeListOfAppointments, "frag_CompleteListOfAppointments");
        fragmentTransaction.replace(R.id.frame_container, myProfile);
        fragmentTransaction.commit();
        VMSConstants.mysysout("account");

    }

}
