package com.visitormanagementsystem.appointments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.ViewAppointment;
import com.visitormanagementsystem.pojos.AppointmentDetailsByVisitorIdPojo;
import com.visitormanagementsystem.utils.MyCustomeToast;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ViewAppointmentHostory extends Fragment implements OnClickListener, Validator.ValidationListener {
    java.sql.Date sqlDate, sqlDateto = null;
    ImageView sliderpanel_image, back;
    PostClient postClient;
    String photoUrl = null, viewappt_visitorname = null, viewappt_epid = null, viewappt_company = null, viewappt_desg = null, viewappt_nationality = null, viewappt_category = null,
            viewappt_dob = null, viewappt_gender = null, viewappt_apid = null;
    Validator validator;
    private static String CSV = null;
    TextView tvGoBack;
    Fragment imr;
    Bundle bundle;
    @Bind(R.id.singlerecord_aprovedby)
    TextView singlerecord_aprovedby;

    @Bind(R.id.singlerecord_aproveddate)
    TextView singlerecord_aproveddate;

    @Bind(R.id.singlerecord_aptstatus)
    TextView singlerecord_aptstatus;

    @Bind(R.id.singlerecord_category)
    TextView singlerecord_category;

    @Bind(R.id.singlerecord_comments)
    TextView singlerecord_comments;

    @Bind(R.id.singlerecord_company)
    TextView singlerecord_company;

    @Bind(R.id.singlerecord_desg)
    TextView singlerecord_desg;

    @Bind(R.id.singlerecord_dob)
    TextView singlerecord_dob;

    @Bind(R.id.singlerecord_duration)
    TextView singlerecord_duration;

    @Bind(R.id.singlerecord_endtime)
    TextView singlerecord_endtime;

    @Bind(R.id.singlerecord_epid)
    TextView singlerecord_epid;

    @NotEmpty
    @Bind(R.id.singlerecord_gender)
    TextView singlerecord_gender;

    @Bind(R.id.singlerecord_name)
    TextView singlerecord_name;

    @Bind(R.id.singlerecord_nationality)
    TextView singlerecord_nationality;

    @Bind(R.id.singlerecord_recappt)
    TextView singlerecord_recappt;

    @Bind(R.id.singlerecord_starttime)
    TextView singlerecord_starttime;
    @Bind(R.id.singlerecord_profileimage)
    ImageView singlerecord_profileimage;

    @Bind(R.id.actions_accept_reject_layout)
    LinearLayout actions_accept_reject_layout;
    ProgressDialog progressDialog;

    @Bind(R.id.cardview_appt_history_item_card_view3)
    CardView cardview_appt_history_item_card_view3;

    @Bind(R.id.viewappt__empapprovaledit_item_card_view5)
    CardView viewappt__empapprovaledit_item_card_view5;

    @Bind(R.id.viewappt_approve)
    TextView viewappt_approve;
    @Bind(R.id.viewappt_reject)
    TextView viewappt_reject;
    List<String> list = new ArrayList<>();

    String date_from_date, date_too_date;
    Date sch_date_from, sch_date_to = null;
    String date_from;
    String date_too;
    String time_froom;
    String time_too;
    int rec_type;
    int day_type_value;
    int actionevent, actionevent_rej;
    StringBuilder MonthlyWeeks_type_value, WeekDay_type_value, YearlyOptions_type_value;
    String rec_every_days;
    String rec_weektype;
    String rec_daystype;
    String rec_everymonth;
    String rec_firsttolast;
    String rec_sattosunday;
    String rec_jantodec;
    boolean isRecurringChecked = false;
    String nations_array[] = {"--Select--", "Daily", "Weekly", "Monthly", "Yearly"};
    String days_array[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String weekType_array[] = {"first", "second", "third", "fourth", "last"};
    String weekDays_array[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String oneToTwelve_array[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String firstToLast_array[] = {"first", "second", "third", "fourth", "last"};
    String sundayToSaturday_array[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String janToDec_array[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};

    @Bind(R.id.viewappt_idType)
    Spinner viewappt_idType;

    @Bind(R.id.viewappt_daysType)
    Spinner viewappt_daysType;


    @Bind(R.id.viewappt_weekType)
    Spinner viewappt_weekType;

    @Bind(R.id.viewappt_weekDays)
    Spinner viewappt_weekDays;


    @Bind(R.id.viewappt_onetotwelve)
    Spinner viewappt_onetotwelve;

    @Bind(R.id.viewappt_firstToLast)
    Spinner viewappt_firstToLast;


    @Bind(R.id.viewappt_sundayToSaturday)
    Spinner viewappt_sundayToSaturday;

    @Bind(R.id.viewappt_janToDec)
    Spinner viewappt_janToDec;
    @Bind(R.id.viewappt_date_picker)
    ImageView viewappt_date_picker;

    @Bind(R.id.viewappt_date_picker_to)
    ImageView viewappt_date_picker_to;

    @Bind(R.id.viewappt_time_from)
    EditText viewappt_time_from;


    @Bind(R.id.viewappt_time_to)
    EditText viewappt_time_to;
    @Bind(R.id.viewappt_date_picker_text)
    TextView viewappt_date_picker_text;

    @Bind(R.id.viewappt_date_picker_text_to)
    TextView viewappt_date_picker_text_to;


    @Bind(R.id.viewappt_chkAppointment)
    CheckBox viewappt_chkAppointment;

    @Bind(R.id.viewappt_linearLayout1)
    LinearLayout viewappt_linearLayout1;

    @Bind(R.id.viewappt_dailyLinearLayout)
    LinearLayout viewappt_dailyLinearLayout;

    @Bind(R.id.viewappt_weeklyLinearLayout)
    LinearLayout viewappt_weeklyLinearLayout;
    @Bind(R.id.viewappt_monthlyLinearLayout)
    LinearLayout viewappt_monthlyLinearLayout;
    @Bind(R.id.viewappt_yearlyLinearLayout)
    LinearLayout viewappt_yearlyLinearLayout;

    @Bind(R.id.viewappt_dayscount)
    EditText viewappt_dayscount;

    @Bind(R.id.viewappt_hrscount)
    EditText viewappt_hrscount;

    @Bind(R.id.singlerecord_accompanywith)
    TextView singlerecord_accompanywith;
    @Bind(R.id.singlerecord_prerequisite)
    TextView singlerecord_prerequisite;
    @Bind(R.id.accompanywith_ll)
    LinearLayout accompanywith_ll;
    @Bind(R.id.meetingprerq_ll)
    LinearLayout meetingprerq_ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.view_appointment_history, null);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.please_wait_progress));
        progressDialog.setCancelable(false);
        ButterKnife.bind(this, view);

        validator = new Validator(this);
        validator.setValidationListener(this);

        tvGoBack = (TextView) view.findViewById(R.id.goBack);
        tvGoBack.setOnClickListener(this);
        tvGoBack.setVisibility(View.GONE);


        bundle = new Bundle();


        ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                nations_array);
        nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_idType.setAdapter(nations_adapter);

        ArrayAdapter<String> days_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                days_array);
        days_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_daysType.setAdapter(days_adapter);
        viewappt_daysType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_every_days = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> weekType_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                weekType_array);
        weekType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_weekType.setAdapter(weekType_adapter);
        viewappt_weekType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_weektype = parent.getItemAtPosition(position).toString();
                MonthlyWeeks_type_value = new StringBuilder();
                switch (rec_weektype) {
                    case "first":
                        // MonthlyWeeks_type_value = String.valueOf(VMSConstants.first);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.first));

                        break;
                    case "second":
                        //MonthlyWeeks_type_value = String.valueOf(VMSConstants.second);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.second));
                        break;
                    case "third":
                        //  MonthlyWeeks_type_value = String.valueOf(VMSConstants.third);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.third));
                        break;
                    case "fourth":
                        // MonthlyWeeks_type_value = String.valueOf(VMSConstants.fourth);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.fourth));
                        break;
                    case "last":
                        // MonthlyWeeks_type_value = String.valueOf(VMSConstants.last);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.last));
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> weekDays_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                weekDays_array);
        weekDays_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_weekDays.setAdapter(weekDays_adapter);
        viewappt_weekDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_daystype = parent.getItemAtPosition(position).toString();
                WeekDay_type_value = new StringBuilder();
                switch (rec_daystype) {
                    case "Sunday":
                        // WeekDay_type_value = String.valueOf(VMSConstants.Sunday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Sunday));
                        break;
                    case "Monday":
                        //WeekDay_type_value = String.valueOf(VMSConstants.Monday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Monday));
                        break;
                    case "Tuesday":
                        // WeekDay_type_value = String.valueOf(VMSConstants.Tuesday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Tuesday));
                        break;
                    case "Wednesday":
                        // WeekDay_type_value = String.valueOf(VMSConstants.Wednesday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Wednesday));
                        break;
                    case "Thursday":
                        //  WeekDay_type_value = String.valueOf(VMSConstants.Thursday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Thursday));
                        break;
                    case "Friday":
                        // WeekDay_type_value = String.valueOf(VMSConstants.Friday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Friday));
                        break;
                    case "Saturday":
                        // WeekDay_type_value = String.valueOf(VMSConstants.Saturday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Saturday));
                        break;
                }
            }

            //{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> oneToTwelve_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                oneToTwelve_array);
        oneToTwelve_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_onetotwelve.setAdapter(oneToTwelve_adapter);
        viewappt_onetotwelve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_everymonth = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> firstToLast_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                firstToLast_array);
        firstToLast_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_firstToLast.setAdapter(firstToLast_adapter);
        viewappt_firstToLast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_firsttolast = parent.getItemAtPosition(position).toString();
                MonthlyWeeks_type_value = new StringBuilder();

                switch (rec_firsttolast) {
                    case "first":
                        //  MonthlyWeeks_type_value = String.valueOf(VMSConstants.first);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.first));

                        break;
                    case "second":
                        // MonthlyWeeks_type_value = String.valueOf(VMSConstants.second);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.second));
                        break;
                    case "third":
                        //  MonthlyWeeks_type_value = String.valueOf(VMSConstants.third);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.third));
                        break;
                    case "fourth":
                        // MonthlyWeeks_type_value = String.valueOf(VMSConstants.fourth);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.fourth));
                        break;
                    case "last":
                        //  MonthlyWeeks_type_value = String.valueOf(VMSConstants.last);
                        MonthlyWeeks_type_value.append(String.valueOf(VMSConstants.last));
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> sundayToSaturday_adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.sp_selectquestion, sundayToSaturday_array);
        sundayToSaturday_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_sundayToSaturday.setAdapter(sundayToSaturday_adapter);
        viewappt_sundayToSaturday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_sattosunday = parent.getItemAtPosition(position).toString();
                WeekDay_type_value = new StringBuilder();

                switch (rec_sattosunday) {
                    case "Sunday":
                        //  WeekDay_type_value = String.valueOf(VMSConstants.Sunday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Sunday));
                        break;
                    case "Monday":
                        //WeekDay_type_value = String.valueOf(VMSConstants.Monday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Monday));
                        break;
                    case "Tuesday":
                        // WeekDay_type_value = String.valueOf(VMSConstants.Tuesday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Tuesday));
                        break;
                    case "Wednesday":
                        //    WeekDay_type_value = String.valueOf(VMSConstants.Wednesday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Wednesday));
                        break;
                    case "Thursday":
                        //   WeekDay_type_value = String.valueOf(VMSConstants.Thursday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Thursday));
                        break;
                    case "Friday":
                        //  WeekDay_type_value = String.valueOf(VMSConstants.Friday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Friday));
                        break;
                    case "Saturday":
                        // WeekDay_type_value = String.valueOf(VMSConstants.Saturday);
                        WeekDay_type_value.append(String.valueOf(VMSConstants.Saturday));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> janToDec_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                janToDec_array);
        janToDec_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewappt_janToDec.setAdapter(janToDec_adapter);

        viewappt_janToDec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_jantodec = parent.getItemAtPosition(position).toString();
                YearlyOptions_type_value = new StringBuilder();

                switch (rec_jantodec) {
                    case "January":
                        // YearlyOptions_type_value = String.valueOf(VMSConstants.January);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.January));
                        break;
                    case "February":
                        // YearlyOptions_type_value = String.valueOf(VMSConstants.February);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.February));
                        break;
                    case "March":
                        // YearlyOptions_type_value = String.valueOf(VMSConstants.March);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.March));
                        break;
                    case "April":
                        //YearlyOptions_type_value = String.valueOf(VMSConstants.April);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.April));
                        break;
                    case "May":
                        //YearlyOptions_type_value = String.valueOf(VMSConstants.May);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.May));
                        break;
                    case "June":
                        // YearlyOptions_type_value = String.valueOf(VMSConstants.June);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.June));
                        break;
                    case "July":
                        // YearlyOptions_type_value = String.valueOf(VMSConstants.July);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.July));
                        break;

                    case "August":
                        // YearlyOptions_type_value = String.valueOf(VMSConstants.August);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.August));
                        break;
                    case "September":
                        //    YearlyOptions_type_value = String.valueOf(VMSConstants.September);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.September));
                        break;
                    case "October":
                        //  YearlyOptions_type_value = String.valueOf(VMSConstants.October);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.October));
                        break;
                    case "November":
                        //YearlyOptions_type_value = String.valueOf(VMSConstants.November);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.November));
                        break;
                    case "December":
                        //  YearlyOptions_type_value = String.valueOf(VMSConstants.December);
                        YearlyOptions_type_value.append(String.valueOf(VMSConstants.December));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewappt_idType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    rec_type = VMSConstants.Daily;
                    // LL Daily
                    viewappt_dailyLinearLayout.setVisibility(View.VISIBLE);
                    viewappt_weeklyLinearLayout.setVisibility(View.GONE);
                    viewappt_monthlyLinearLayout.setVisibility(View.GONE);
                    viewappt_yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 2) {
                    rec_type = VMSConstants.Weekly;
                    // LL Weekly
                    viewappt_dailyLinearLayout.setVisibility(View.GONE);
                    viewappt_weeklyLinearLayout.setVisibility(View.VISIBLE);
                    viewappt_monthlyLinearLayout.setVisibility(View.GONE);
                    viewappt_yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 3) {
                    rec_type = VMSConstants.Monthly;
                    // LL Monthly
                    viewappt_dailyLinearLayout.setVisibility(View.GONE);
                    viewappt_weeklyLinearLayout.setVisibility(View.GONE);

                    viewappt_monthlyLinearLayout.setVisibility(View.VISIBLE);
                    viewappt_yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 4) {
                    rec_type = VMSConstants.Yearly;
                    // LL Yearly
                    viewappt_dailyLinearLayout.setVisibility(View.GONE);
                    viewappt_weeklyLinearLayout.setVisibility(View.GONE);
                    viewappt_monthlyLinearLayout.setVisibility(View.GONE);
                    viewappt_yearlyLinearLayout.setVisibility(View.VISIBLE);
                } else if (position == 0) {

                    // LL Daily
                    viewappt_dailyLinearLayout.setVisibility(View.GONE);
                    viewappt_weeklyLinearLayout.setVisibility(View.GONE);
                    viewappt_monthlyLinearLayout.setVisibility(View.GONE);
                    viewappt_yearlyLinearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        return view;
    }

    @OnCheckedChanged
            ({R.id.viewappt_chkSunday, R.id.viewappt_chkMonday, R.id.viewappt_chkTuesday, R.id.viewappt_chkWednesday, R.id.viewappt_chkThursday,
                    R.id.viewappt_chkFriday, R.id.viewappt_chkSaturday})
    void checkboxvalues(CheckBox currentcheckbox) {
        if (currentcheckbox.isChecked()) {
            System.out.println(" Sunday value " + currentcheckbox.getText().toString());
            switch (currentcheckbox.getText().toString()) {
                case "Sunday":
                    day_type_value = VMSConstants.Sunday;
                    break;
                case "Monday":
                    day_type_value = VMSConstants.Monday;
                    break;
                case "Tuesday":
                    day_type_value = VMSConstants.Tuesday;
                    break;
                case "Wednesday":
                    day_type_value = VMSConstants.Wednesday;
                    break;
                case "Thursday":
                    day_type_value = VMSConstants.Thursday;
                    break;
                case "Friday":
                    day_type_value = VMSConstants.Friday;
                    break;
                case "Saturday":
                    day_type_value = VMSConstants.Saturday;
                    break;


            }
            list.add(String.valueOf(day_type_value));
        } else {
            list.remove(String.valueOf(day_type_value));
        }


    }

    @OnClick(R.id.viewappt_time_from)
    public void timefrom(EditText timefrom) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                viewappt_time_from.setText(selectedHour + ":" + selectedMinute);
                time_froom = selectedHour + ":" + selectedMinute;
            }
        }, hour, minute, true);// Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    @OnClick(R.id.viewappt_time_to)
    public void timeto(EditText timeto) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                time_too = selectedHour + ":" + selectedMinute;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewappt_time_to.setText(time_too);
                        Date d1 = null;
                        Date d2 = null;
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
                        try {
                            String dd1 = date_from + " " + time_froom;
                            String dd2 = date_too + " " + time_too;
                            d1 = format.parse(dd1);
                            d2 = format.parse(dd2);

                            //in milliseconds
                            long diff = d2.getTime() - d1.getTime();

                            long diffSeconds = diff / 1000 % 60;
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;
                            long diffDays = diff / (24 * 60 * 60 * 1000);

                            System.out.print(diffDays + " days, ");
                            System.out.print(diffHours + " hours, ");
                            System.out.print(diffMinutes + " minutes, ");
                            System.out.print(diffSeconds + " seconds.");
                            if (diffMinutes == 0) {
                                MyCustomeToast.show(getActivity(), "time should not be the same!!", true, false);
                                viewappt_time_to.setText(getActivity().getString(R.string.endtime2));
                            } else {

                                viewappt_dayscount.setText(String.valueOf(diffDays) + " day(s).");
                                viewappt_hrscount.setText(String.valueOf(diffHours) + " hrs. & " + String.valueOf(diffMinutes) + " mins.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }, hour, minute, true);// Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }


    final Calendar myCalendar = Calendar.getInstance();

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {
//viewappt_date_picker_text


            String myFormat = "dd/MM/yy"; // In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                Date date = sdf.parse(sdf.format(myCalendar.getTime()));


                sch_date_from = date;

                Gson gson = new GsonBuilder().setDateFormat("E MMM dd HH:mm:ss Z yyyy").create();
                String aptfromdate = "\"" + String.valueOf(sch_date_from) + "\"";


                Date fromdate = gson.fromJson(aptfromdate, Date.class);

                java.sql.Date fromdate_afterdate_val = new java.sql.Date(fromdate.getTime());
                sqlDate = new java.sql.Date(fromdate.getTime());
                System.out.println("***************\n sqlDatefrom  " + sqlDate + "  " + fromdate_afterdate_val.getTime() + "\n *********************");

                date_from = sdf.format(myCalendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String displaydateformat = "dd MMM yyyy";
            SimpleDateFormat sdf1 = new SimpleDateFormat(displaydateformat, Locale.US);
            date_from_date = sdf1.format(myCalendar.getTime());
            try {


                if (sdf1.parse(date_from_date).before(sdf1.parse(sdf1.format(new Date())))) {

                    viewappt_date_picker_text.setText("Date");

                    MyCustomeToast.show(getActivity(), getActivity().getString(R.string.msg_validdate), false, false);
                } else {
                    viewappt_date_picker_text.setText(date_from_date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //viewappt_date_picker_text.setText(date_from_date);
        }

    };


    @OnClick(R.id.viewappt_date_picker)
    public void fun_viewapptdatepicer(ImageView imageView) {
        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    final DatePickerDialog.OnDateSetListener date_to = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel_to();
        }

        private void updateLabel_to() {
//viewappt_date_picker_text_to


            String myFormat = "dd/MM/yy"; // In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                Date date = sdf.parse(sdf.format(myCalendar.getTime()));
                date_too = sdf.format(myCalendar.getTime());

                sch_date_to = date;
                Gson gson = new GsonBuilder().setDateFormat("E MMM dd HH:mm:ss Z yyyy").create();
                String aptfromdate = "\"" + String.valueOf(sch_date_to) + "\"";

                System.out.println("*******date: before  \n" + date + " \n " + aptfromdate + "\n ********");

                Date test = gson.fromJson(aptfromdate, Date.class);
                System.out.println("date: after " + test);
                java.sql.Date afterdateval = new java.sql.Date(test.getTime());
                sqlDateto = new java.sql.Date(test.getTime());
                System.out.println("*********************\n sqlDateto  " + sqlDateto + "  " + afterdateval.getTime() + "\n***********************");

               /*     // DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
                    DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    DateFormat dateFormatNeeded =new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    try {
                        String str_d = userDateFormat.format(afterdateval);
                        Date date_ = userDateFormat.parse(str_d);
                        String finaldate = dateFormatNeeded.format(date_);
                        Date displaydate = dateFormatNeeded.parse(finaldate);

                        sqlDateto = new java.sql.Date(displaydate.getTime());
                        System.out.println("*******************************" + "\n" + str_d + "\n" + date_ + "\n" + finaldate + "\n" + displaydate + "\n todate" + sqlDateto + "\n" + "***********************************");


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/


            } catch (ParseException e) {
                e.printStackTrace();
            }
            String displaydateformat = "dd MMM yyyy";
            SimpleDateFormat sdf1 = new SimpleDateFormat(displaydateformat, Locale.US);
            date_too_date = sdf1.format(myCalendar.getTime());
            try {


                if (sdf1.parse(date_too_date).before(sdf1.parse(sdf1.format(new Date())))) {

                    viewappt_date_picker_text_to.setText("Date");

                    MyCustomeToast.show(getActivity(), getResources().getString(R.string.msg_validdate), false, false);
                } else {
                    viewappt_date_picker_text_to.setText(date_too_date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //  viewappt_date_picker_text_to.setText(sdf1.format(myCalendar.getTime()));
        }

    };

    @OnClick(R.id.viewappt_date_picker_to)
    public void img_viewappt_date_picker_to(ImageView imageView) {
        new DatePickerDialog(getActivity(), date_to, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.viewappt_chkAppointment)
    public void fun_viewappt_chkAppointment(CheckBox checkBox) {
        if (viewappt_chkAppointment.isChecked()) {
            isRecurringChecked = true;
            viewappt_linearLayout1.setVisibility(View.VISIBLE);
            viewappt_dailyLinearLayout.setVisibility(View.GONE);
            viewappt_weeklyLinearLayout.setVisibility(View.GONE);
            viewappt_monthlyLinearLayout.setVisibility(View.GONE);
            viewappt_yearlyLinearLayout.setVisibility(View.GONE);

        } else {
            isRecurringChecked = false;
            viewappt_linearLayout1.setVisibility(View.GONE);
            viewappt_dailyLinearLayout.setVisibility(View.GONE);
            viewappt_weeklyLinearLayout.setVisibility(View.GONE);
            viewappt_monthlyLinearLayout.setVisibility(View.GONE);
            viewappt_yearlyLinearLayout.setVisibility(View.GONE);

        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressDialog.show();

        postClient = new PostClient();
        String AppointmentID = Prefs.getString("AppointmentID", "");

        getActivity().setTitle(Prefs.getString("PublicAppointmentID", ""));
        progressDialog.dismiss();
        Call<AppointmentDetailsByVisitorIdPojo> appointmentDetailsByVisitorIdPojoCall = postClient.myApiEndpointInterface.getAppointmentDetailsById(AppointmentID);
        appointmentDetailsByVisitorIdPojoCall.enqueue(new Callback<AppointmentDetailsByVisitorIdPojo>() {

            @Override
            public void onResponse(Response<AppointmentDetailsByVisitorIdPojo> response, Retrofit retrofit) {
                if (response.isSuccess() == true) {


                    AppointmentDetailsByVisitorIdPojo appointmentDetailsByVisitorIdPojo = response.body();


                    VMSConstants.mysysout("APid " + appointmentDetailsByVisitorIdPojo.getAppointmentID() + "\n" + response.raw() + "\n" +
                            appointmentDetailsByVisitorIdPojo.getAccompanyWith());
                    viewappt_apid = appointmentDetailsByVisitorIdPojo.getAppointmentID();

                    String photostring = appointmentDetailsByVisitorIdPojo.getPhoto().toString();
                    photoUrl = photostring;
                    if (photostring != null) {

                        byte[] imageAsBytes = Base64.decode(photostring.getBytes(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                        singlerecord_profileimage.setImageBitmap(bitmap);
                    }
                    if (appointmentDetailsByVisitorIdPojo.getVisitorName() != null) {
                        singlerecord_name.setText(appointmentDetailsByVisitorIdPojo.getVisitorName());
                        viewappt_visitorname = appointmentDetailsByVisitorIdPojo.getVisitorName();

                    }
                    if (appointmentDetailsByVisitorIdPojo.getEmployeeID() != null) {
                        singlerecord_epid.setText(appointmentDetailsByVisitorIdPojo.getEmployeeID());
                        viewappt_epid = appointmentDetailsByVisitorIdPojo.getEmployeeID();

                    }
                    if (appointmentDetailsByVisitorIdPojo.getCompanyName() != null) {
                        singlerecord_company.setText(appointmentDetailsByVisitorIdPojo.getCompanyName());
                        viewappt_company = appointmentDetailsByVisitorIdPojo.getCompanyName();

                    }
                    if (appointmentDetailsByVisitorIdPojo.getDesignation() != null) {
                        singlerecord_desg.setText(appointmentDetailsByVisitorIdPojo.getDesignation());
                        viewappt_desg = appointmentDetailsByVisitorIdPojo.getDesignation();

                    }
                    if (appointmentDetailsByVisitorIdPojo.getNationality() != null) {
                        singlerecord_nationality.setText(appointmentDetailsByVisitorIdPojo.getNationality());
                        viewappt_nationality = appointmentDetailsByVisitorIdPojo.getNationality();

                    }
                    if (appointmentDetailsByVisitorIdPojo.getSectorCompCategory() != null) {
                        singlerecord_category.setText(appointmentDetailsByVisitorIdPojo.getSectorCompCategory());
                        viewappt_category = appointmentDetailsByVisitorIdPojo.getSectorCompCategory();
                    }
                    if (appointmentDetailsByVisitorIdPojo.getAccompanyWith() != null) {
                        singlerecord_accompanywith.setText(appointmentDetailsByVisitorIdPojo.getAccompanyWith());
                    }
                    if (appointmentDetailsByVisitorIdPojo.getMeetingPrerequisite() != null) {
                        singlerecord_prerequisite.setText(appointmentDetailsByVisitorIdPojo.getMeetingPrerequisite());
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
                    Date formattedDOBdate = null, formattedStartdate = null, formattedEnddate = null;
                    try {
                        formattedEnddate = sdf.parse(appointmentDetailsByVisitorIdPojo.getEndDate());
                        formattedStartdate = sdf.parse(appointmentDetailsByVisitorIdPojo.getStartDate());
                        if (appointmentDetailsByVisitorIdPojo.getDOB() != null) {
                            formattedDOBdate = sdf.parse(appointmentDetailsByVisitorIdPojo.getDOB());
                        } else {
                            formattedDOBdate = null;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {

                        String formattedDOBTime = output.format(formattedDOBdate);
                        if (formattedDOBTime != null) {
                            singlerecord_dob.setText(formattedDOBTime);
                            viewappt_dob = formattedDOBTime;

                        }
                    } catch (Exception e) {

                    }
                    if (appointmentDetailsByVisitorIdPojo.getGender() != null) {
                        singlerecord_gender.setText(appointmentDetailsByVisitorIdPojo.getGender());
                        viewappt_gender = appointmentDetailsByVisitorIdPojo.getGender();

                    }


                    String formattedEndTime = output.format(formattedEnddate);
                    String formattedStartTime = output.format(formattedStartdate);

                    System.out.println(formattedEnddate + " formattedTime  " + formattedEndTime);

                    if (formattedEndTime != null || appointmentDetailsByVisitorIdPojo.getStartTime() != null) {

                        singlerecord_starttime.setText(formattedStartTime + " " + appointmentDetailsByVisitorIdPojo.getStartTime());

                    }
                    if (formattedEndTime != null || appointmentDetailsByVisitorIdPojo.getEndTime() != null) {
                        singlerecord_endtime.setText(formattedEndTime + " " + appointmentDetailsByVisitorIdPojo.getEndTime());

                    }

                    if (appointmentDetailsByVisitorIdPojo.getDays() != null || appointmentDetailsByVisitorIdPojo.getDuration() != null) {
                        singlerecord_duration.setText(appointmentDetailsByVisitorIdPojo.getDays() + " " + appointmentDetailsByVisitorIdPojo.getDuration());

                    }

                    String recvalue = appointmentDetailsByVisitorIdPojo.getRecurringType() + " " + appointmentDetailsByVisitorIdPojo.getRecurringInput();

                    String splitvalue = appointmentDetailsByVisitorIdPojo.getRecurringInput();
                    StringBuilder stringBuilder = new StringBuilder();


                    if (splitvalue != null) {
                        List<String> recurringIp = Arrays.asList(splitvalue.split("\\s*,\\s*"));


                        if (appointmentDetailsByVisitorIdPojo.getRecurringType().equalsIgnoreCase(String.valueOf(VMSConstants.Daily))) {

                            stringBuilder.append(" Daily\n");


                        } else if (appointmentDetailsByVisitorIdPojo.getRecurringType().equalsIgnoreCase(String.valueOf(VMSConstants.Weekly))) {
                            stringBuilder.append(" Weekly\n");
                            for (int i = 0; i < recurringIp.size(); i++) {
                                if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.Sunday))) {

                                    stringBuilder.append(" Sun");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.Monday))) {

                                    stringBuilder.append(" Mon");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.Tuesday))) {

                                    stringBuilder.append(" Tue");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.Wednesday))) {

                                    stringBuilder.append(" Wed");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.Thursday))) {

                                    stringBuilder.append(" Thu");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.Friday))) {

                                    stringBuilder.append(" Fri");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.Saturday))) {

                                    stringBuilder.append(" Sat");
                                } else {

                                }

                            }


                        } else if (appointmentDetailsByVisitorIdPojo.getRecurringType().equalsIgnoreCase(String.valueOf(VMSConstants.Monthly))) {
                            stringBuilder.append(" Monthly\n");

                            for (int i = 0; i < recurringIp.size(); i++) {
                                if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.first))) {

                                    stringBuilder.append(" 1st");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.second))) {

                                    stringBuilder.append(" 2nd");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.third))) {

                                    stringBuilder.append(" 3rd");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.fourth))) {

                                    stringBuilder.append(" 4th");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.last))) {

                                    stringBuilder.append(" Last");
                                } else {

                                }

                            }


                        } else if (appointmentDetailsByVisitorIdPojo.getRecurringType().equalsIgnoreCase(String.valueOf(VMSConstants.Yearly))) {
                            stringBuilder.append(" Yearly\n");
                            for (int i = 0; i < recurringIp.size(); i++) {
                                if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.January))) {

                                    stringBuilder.append(" Jan");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.February))) {

                                    stringBuilder.append(" Feb");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.March))) {

                                    stringBuilder.append(" Mar");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.April))) {

                                    stringBuilder.append(" Apr");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.May))) {

                                    stringBuilder.append(" May");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.June))) {

                                    stringBuilder.append(" Jun");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.July))) {

                                    stringBuilder.append(" Jul");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.August))) {

                                    stringBuilder.append(" Aug");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.September))) {

                                    stringBuilder.append(" Sep");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.October))) {

                                    stringBuilder.append(" OCT");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.November))) {

                                    stringBuilder.append(" Nov");
                                } else if (recurringIp.get(i).toString().equalsIgnoreCase(String.valueOf(VMSConstants.December))) {

                                    stringBuilder.append(" Dec");
                                }

                            }
                        } else {

                        }

                        System.out.println("stringBuilder " + stringBuilder);
                    } else {
                        stringBuilder.append("");
                    }
                    if (stringBuilder != null) {
                        singlerecord_recappt.setText(stringBuilder);

                    }
                    if (appointmentDetailsByVisitorIdPojo.getAppointmentStatusName() != null) {
                        singlerecord_aptstatus.setText(appointmentDetailsByVisitorIdPojo.getAppointmentStatusName());

                    }
                    if (appointmentDetailsByVisitorIdPojo.getApprovedBy() != null) {
                        singlerecord_aprovedby.setText(appointmentDetailsByVisitorIdPojo.getApprovedBy());

                    }
                    if (appointmentDetailsByVisitorIdPojo.getApproveDate() != null) {
                        singlerecord_aproveddate.setText(appointmentDetailsByVisitorIdPojo.getApproveDate());

                    }
                    if (appointmentDetailsByVisitorIdPojo.getApprovalComments() != null) {

                        singlerecord_comments.setText(appointmentDetailsByVisitorIdPojo.getApprovalComments());
                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Try Again ", Toast.LENGTH_SHORT).show();
            }


        });

        if (Prefs.getString("userRoleId", "").equalsIgnoreCase("5")) {
            actions_accept_reject_layout.setVisibility(View.VISIBLE);

            if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_todays))) {
                viewappt_approve.setText(R.string.action_reschedule);
                viewappt_reject.setText(R.string.action_cancel);


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_approved))) {

                viewappt_approve.setVisibility(View.GONE);
                viewappt_reject.setText(R.string.action_cancel);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_pending))) {
                VMSConstants.mysysout("inside pending");
                viewappt_approve.setText(R.string.action_reschedule);
                actionevent = VMSConstants.Appt_reschedule;
                viewappt_reject.setText(R.string.action_cancel);
                actionevent_rej = VMSConstants.Appt_cancel;


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_rejected))) {
                viewappt_approve.setText(R.string.action_reschedule);
                actionevent = VMSConstants.Appt_reschedule;
                viewappt_reject.setText(R.string.action_cancel);
                actionevent_rej = VMSConstants.Appt_cancel;

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_cancelled))) {
                viewappt_approve.setText(R.string.action_reschedule);
                actionevent = VMSConstants.Appt_reschedule;
                viewappt_reject.setVisibility(View.GONE);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_attended))) {
                //viewappt_approve.setText(R.string.action_reschedule);
                viewappt_approve.setVisibility(View.GONE);
                viewappt_reject.setText(R.string.action_cancel);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_notattended))) {
                viewappt_approve.setText(R.string.action_reschedule);
                actionevent = VMSConstants.Appt_reschedule;
                viewappt_reject.setText(R.string.action_cancel);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_save))) {
                viewappt_approve.setText(R.string.action_edit);
                viewappt_reject.setText(R.string.action_cancel);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_inprogress))) {

                viewappt_approve.setVisibility(View.GONE);
                viewappt_reject.setText(R.string.action_cancel);
            }
        } else if (Prefs.getString("userRoleId", "").equalsIgnoreCase("2")) {
            actions_accept_reject_layout.setVisibility(View.VISIBLE);

            if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_todays))) {
                viewappt_approve.setVisibility(View.GONE);


                viewappt_reject.setVisibility(View.GONE);


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_approved))) {


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_rejected))) {
                viewappt_approve.setVisibility(View.GONE);


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_cancelled))) {
                viewappt_approve.setVisibility(View.GONE);
                viewappt_reject.setText(R.string.action_cancel);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_peopleoverstay))) {


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_expired))) {


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_todayscheckout))) {
                viewappt_approve.setText("CheckOut");
                viewappt_reject.setVisibility(View.GONE);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_todayscheckin))) {
                viewappt_approve.setText("CheckIn");


                viewappt_reject.setVisibility(View.GONE);
            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_tobeapproved))) {

            }

        } else if (Prefs.getString("userRoleId", "").equalsIgnoreCase("4")) {
            actions_accept_reject_layout.setVisibility(View.VISIBLE);

            if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_todays))) {
                viewappt_approve.setVisibility(View.GONE);


                viewappt_reject.setVisibility(View.GONE);


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_approved))) {


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_pending))) {
                viewappt_approve.setVisibility(View.GONE);


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_rejected))) {
                viewappt_approve.setVisibility(View.GONE);


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_cancelled))) {
                viewappt_approve.setVisibility(View.GONE);
                viewappt_reject.setText(R.string.action_cancel);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_attended))) {


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_notattended))) {


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_save))) {
                viewappt_approve.setText("CheckOut");
                viewappt_reject.setVisibility(View.GONE);

            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_inprogress))) {
                viewappt_approve.setText("CheckIn");


                viewappt_reject.setVisibility(View.GONE);
            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_tobeapproved))) {
                VMSConstants.mysysout("inside tobe approved");
                meetingprerq_ll.setVisibility(View.VISIBLE);
                accompanywith_ll.setVisibility(View.VISIBLE);
                actionevent = VMSConstants.FirstLevelAppointmentApprove;


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_firstlevelappointmentapprove))) {
                viewappt_approve.setVisibility(View.GONE);


            } else if (Prefs.getString("ED_ActionName", "").equalsIgnoreCase(getActivity().getString(R.string.status_delegated))) {
                viewappt_approve.setVisibility(View.GONE);


            }


        }

    }

    @OnClick(R.id.viewappt_approve)
    public void action_reSchedule(TextView textView) {
        switch (actionevent) {
            case VMSConstants.Appt_reschedule:
                if (viewappt_date_picker_text.getText().length() > 7 && viewappt_date_picker_text_to.getText().length() > 7 && viewappt_time_from.getText().length() > 0 && viewappt_time_to.getText().length() > 0) {

                    validator.validate();
                } else {
                    VMSConstants.ShowSnackbar(getView(), getActivity().getString(R.string.selectdate));

                }
                cardview_appt_history_item_card_view3.setVisibility(View.GONE);
                viewappt__empapprovaledit_item_card_view5.setVisibility(View.VISIBLE);
                break;
            case VMSConstants.FirstLevelAppointmentApprove:
                VMSConstants.mysysout("FirstLevelAppointmentApprove");
                if (viewappt_date_picker_text.getText().length() > 7 && viewappt_date_picker_text_to.getText().length() > 7 && viewappt_time_from.getText().length() > 0 && viewappt_time_to.getText().length() > 0) {

                    validator.validate();
                } else {
                    VMSConstants.ShowSnackbar(getView(), getActivity().getString(R.string.selectdate));

                }
                cardview_appt_history_item_card_view3.setVisibility(View.GONE);
                viewappt__empapprovaledit_item_card_view5.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }


    }

    @OnClick(R.id.viewappt_reject)

    public void action_cancel(TextView textView) {
        switch (actionevent_rej) {
            case VMSConstants.Appt_cancel:
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setCancelable(false);
                alertDialog.setTitle(getString(R.string.exit));
                //alertDialog.setMessage(getString(R.string.wanttoexit));
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cardview_appt_history_item_card_view3.setVisibility(View.VISIBLE);

                        viewappt__empapprovaledit_item_card_view5.setVisibility(View.GONE);
                    }
                });

                alertDialog.show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.equals(tvGoBack)) {
            imr = new ViewAppointment();
            if (imr != null) {
               /* FragmentManager fmr = getFragmentManager();
                fmr.beginTransaction().replace(R.id.frame_container, imr).addToBackStack(null).commit();
                bundle.putString("servicename", "Approved");
                imr.setArguments(bundle);*/
                getActivity().getFragmentManager().popBackStack();
            }

//			getActivity().getFragmentManager().popBackStack();

        }
    }

    @Override
    public void onValidationSucceeded() {
        if (VMSConstants.Appt_reschedule == 1001) {
            viewappt_reject.setEnabled(true);


            fun_reschduleAppointment();

        } else if (VMSConstants.Appt_cancel == 1002) {

        } else if (VMSConstants.FirstLevelAppointmentApprove == 103) {
            fun_emp_firstlevel_approve();
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }


    private void fun_reschduleAppointment() {
        progressDialog.show();
        if (list.size() > 0) {

            CSV = android.text.TextUtils.join(",", list);
            System.out.println("DAys " + list.toString() + " cms  " + CSV);
            System.out.println(list.toString());
        }
        //SCH_PREFS
        String RecurringInput = null;

        switch (rec_type) {
            case 101:
                RecurringInput = rec_every_days;
                break;
            case 102:
                RecurringInput = CSV;
                break;
            case 103:
                RecurringInput = MonthlyWeeks_type_value + "," + WeekDay_type_value + "," + rec_everymonth;
                break;
            case 104:
                RecurringInput = MonthlyWeeks_type_value + "," + WeekDay_type_value + "," + YearlyOptions_type_value;


        }

        AppointmentDetailsByVisitorIdPojo appointmentListByVisitorId = new AppointmentDetailsByVisitorIdPojo(viewappt_apid, viewappt_visitorname, viewappt_epid,
                viewappt_company, viewappt_desg, viewappt_nationality, viewappt_category, photoUrl, viewappt_dob, viewappt_gender, date_from_date, date_too_date, time_froom,
                time_too, viewappt_dayscount.getText().toString(), viewappt_hrscount.getText().toString(), String.valueOf(rec_type), RecurringInput, "", "", isRecurringChecked, "", "", "", "", "", "");

        progressDialog.dismiss();
        Observable<AppointmentDetailsByVisitorIdPojo> appointmentListByVisitorIdObservable = postClient.myApiEndpointInterface.rescheduleAppointmentRx(appointmentListByVisitorId);
        appointmentListByVisitorIdObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<AppointmentDetailsByVisitorIdPojo>() {
            @Override
            public void onCompleted() {
                VMSConstants.mysysout("completed");
                getActivity().getFragmentManager().popBackStack();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                VMSConstants.mysysout("error called ");
            }

            @Override
            public void onNext(AppointmentDetailsByVisitorIdPojo appointmentListByVisitorId) {
                VMSConstants.mysysout("REsponse fdata " + new Gson().toJson(appointmentListByVisitorId));
                MyCustomeToast.show(getActivity(), getActivity().getString(R.string.msg_appointmentupdated), true, true);

            }
        });


        //isRecurringChecked, String.valueOf(rec_type), RecurringInput, date_from_date, date_too_date, time_froom, time_too, viewappt_dayscount.getText().toString(),
        // viewappt_hrscount.getText().toString(), byteArrayImageString

    }

    private void fun_emp_firstlevel_approve() {

        progressDialog.show();
        if (list.size() > 0) {

            CSV = android.text.TextUtils.join(",", list);
            System.out.println("DAys " + list.toString() + " cms  " + CSV);
            System.out.println(list.toString());
        }
        //SCH_PREFS
        String RecurringInput = null;

        switch (rec_type) {
            case 101:
                RecurringInput = rec_every_days;
                break;
            case 102:
                RecurringInput = CSV;
                break;
            case 103:
                RecurringInput = MonthlyWeeks_type_value + "," + WeekDay_type_value + "," + rec_everymonth;
                break;
            case 104:
                RecurringInput = MonthlyWeeks_type_value + "," + WeekDay_type_value + "," + YearlyOptions_type_value;


        }

        AppointmentDetailsByVisitorIdPojo appointmentListByVisitorId = new AppointmentDetailsByVisitorIdPojo(viewappt_apid, viewappt_visitorname, viewappt_epid,
                viewappt_company, viewappt_desg, viewappt_nationality, viewappt_category, photoUrl, viewappt_dob, viewappt_gender, date_from_date, date_too_date, time_froom,
                time_too, viewappt_dayscount.getText().toString(), viewappt_hrscount.getText().toString(), String.valueOf(rec_type), RecurringInput, "", "", isRecurringChecked, "", "", "", "", "", "", singlerecord_accompanywith.getText().toString(),
                singlerecord_prerequisite.getText().toString(), "");

        progressDialog.dismiss();
        Observable<AppointmentDetailsByVisitorIdPojo> appointmentListByVisitorIdObservable = postClient.myApiEndpointInterface.rescheduleAppointmentRx(appointmentListByVisitorId);
        appointmentListByVisitorIdObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<AppointmentDetailsByVisitorIdPojo>() {
            @Override
            public void onCompleted() {
                VMSConstants.mysysout("completed");
                getActivity().getFragmentManager().popBackStack();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                VMSConstants.mysysout("error called ");
            }

            @Override
            public void onNext(AppointmentDetailsByVisitorIdPojo appointmentListByVisitorId) {
                VMSConstants.mysysout("REsponse approve fdata " + new Gson().toJson(appointmentListByVisitorId));
                MyCustomeToast.show(getActivity(), getActivity().getString(R.string.msg_appointmentupdated), true, true);

            }
        });


        //isRecurringChecked, String.valueOf(rec_type), RecurringInput, date_from_date, date_too_date, time_froom, time_too, viewappt_dayscount.getText().toString(),
        // viewappt_hrscount.getText().toString(), byteArrayImageString


    }
}
