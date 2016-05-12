package com.visitormanagementsystem.schedule;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.pixplicity.easyprefs.library.Prefs;
import com.visitormanagementsystem.GDMainActivity;
import com.visitormanagementsystem.R;
import com.visitormanagementsystem.VMSMainActivity;

import com.visitormanagementsystem.pojos.BookAnAppointment;
import com.visitormanagementsystem.utils.MyCustomeToast;
import com.visitormanagementsystem.utils.PostClient;
import com.visitormanagementsystem.utils.VMSConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ScheduleAppointment3 extends Fragment implements OnClickListener, Validator.ValidationListener {
    static final int TIME_DIALOG_ID = 1111;
    private static final int REQUEST_CODE = 256;
    private static String CSV = null;
    String nations_array[] = {"--Select--", "Daily", "Weekly", "Monthly", "Yearly"};
    String days_array[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    java.sql.Date sqlDate, sqlDateto = null;
    String weekType_array[] = {"first", "second", "third", "fourth", "last"};
    String weekDays_array[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String oneToTwelve_array[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String firstToLast_array[] = {"first", "second", "third", "fourth", "last"};
    String sundayToSaturday_array[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String janToDec_array[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    Spinner spIdType, spDaysType, spWeekType, spWeekDays, spOneToTwelve, spFirstToLast, spSundayToSaturday, spJanToDec;
    @NotEmpty
    TextView date_picker_text;
    @NotEmpty

    TextView date_picker_text_to;
    TextView tvPrevious, tvSave, tvSubmit;
    LinearLayout linearLayout1, dailyLinearLayout, weeklyLinearLayout, monthlyLinearLayout, yearlyLinearLayout;
    ImageView sliderpanel_image, date_picker, date_picker_to, back;
    @NotEmpty
    EditText time_from;
    @NotEmpty
    EditText time_to;
    //VMSUtilities iu;
    String date_from_date, date_too_date;
    Date sch_date_from, sch_date_to = null;
    String date_from;
    String date_too;
    String time_froom;
    String time_too;
    int rec_type;
    int day_type_value;

    StringBuilder MonthlyWeeks_type_value, WeekDay_type_value, YearlyOptions_type_value;
    String rec_every_days;
    String rec_weektype;
    String rec_daystype;
    String rec_everymonth;
    String rec_firsttolast;
    String rec_sattosunday;
    String rec_jantodec;
    boolean isRecurringChecked = false;
    List<String> list = new ArrayList<>();
    @Bind(R.id.totaldays)
    EditText totaldays;
    @Bind(R.id.totalhrs)
    EditText totalhrs;

    @Bind(R.id.chkSunday)
    CheckBox chkSunday;
    @Bind(R.id.chkMonday)
    CheckBox chkMonday;

    @Bind(R.id.chkTuesday)
    CheckBox chkTuesday;
    @Bind(R.id.chkWednesday)
    CheckBox chkWednesday;


    @Bind(R.id.chkThursday)
    CheckBox chkThursday;
    @Bind(R.id.chkFriday)
    CheckBox chkFriday;

    @Bind(R.id.chkSaturday)
    CheckBox chkSaturday;
    @Bind(R.id.save)
    TextView save;

    @Bind(R.id.sch3_comments)
    EditText sch3_comments;

    @Bind(R.id.captureImage)
    ImageView captureImage;

    @Bind(R.id.profileImage)
    ImageView profileImage;
    SharedPreferences sharedPreferences;
    byte[] byteArrayImage;
    String byteArrayImageString;
    private CheckBox chkAppointment;
    Validator validator;

    @Bind(R.id.context_title_bar)
    LinearLayout context_title_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.appointment_details, null);
        ButterKnife.bind(this, view);
        validator = new Validator(this);
        validator.setValidationListener(this);

        if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
            context_title_bar.setVisibility(View.VISIBLE);
        }
        spIdType = (Spinner) view.findViewById(R.id.idType);
        spDaysType = (Spinner) view.findViewById(R.id.daysType);

        spWeekType = (Spinner) view.findViewById(R.id.weekType);
        spWeekDays = (Spinner) view.findViewById(R.id.weekDays);
        spOneToTwelve = (Spinner) view.findViewById(R.id.onetotwelve);
        spFirstToLast = (Spinner) view.findViewById(R.id.firstToLast);
        spSundayToSaturday = (Spinner) view.findViewById(R.id.sundayToSaturday);
        spJanToDec = (Spinner) view.findViewById(R.id.janToDec);
        date_picker = (ImageView) view.findViewById(R.id.date_picker);
        date_picker_to = (ImageView) view.findViewById(R.id.date_picker_to);
        date_picker_text = (TextView) view.findViewById(R.id.date_picker_text);
        date_picker_text_to = (TextView) view.findViewById(R.id.date_picker_text_to);

        tvSubmit = (TextView) view.findViewById(R.id.submit);
        tvSubmit.setOnClickListener(this);
        tvPrevious = (TextView) view.findViewById(R.id.previous);
        tvPrevious.setOnClickListener(this);

        time_from = (EditText) view.findViewById(R.id.time_from);
        time_to = (EditText) view.findViewById(R.id.time_to);


        time_from.setKeyListener(null);
        time_to.setKeyListener(null);


        time_from.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_from.setText(selectedHour + ":" + selectedMinute);
                        time_froom = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);// Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        time_to.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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
                                time_to.setText(time_too);
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
                                    totaldays.setText(String.valueOf(diffDays) + " day(s).");
                                    totalhrs.setText(String.valueOf(diffHours) + " hrs. & " + String.valueOf(diffMinutes) + " mins.");
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
        });

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
/*
                    DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    DateFormat dateFormatNeeded = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    try {
                        String str_d = userDateFormat.format(fromdate_afterdate_val);

                        Date date_ = userDateFormat.parse(str_d);

                        String finaldate = dateFormatNeeded.format(date_);

                        Date displaydate = dateFormatNeeded.parse(finaldate);


                        sqlDate = new java.sql.Date(displaydate.getTime());

                        System.out.println("*******************************" + "\n" + str_d + "\n" + date_ + "\n" + finaldate + "\n" + displaydate + "\n fromdate" + sqlDate + "\n" + "***********************************");


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    date_from = sdf.format(myCalendar.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String displaydateformat = "dd MMM yyyy";
                SimpleDateFormat sdf1 = new SimpleDateFormat(displaydateformat, Locale.US);
                date_from_date = sdf1.format(myCalendar.getTime());

                try {


                    if (sdf1.parse(date_from_date).before(sdf1.parse(sdf1.format(new Date())))) {

                        date_picker_text.setText("Date");

                        MyCustomeToast.show(getActivity(), "Select a valid date ", false, false);
                    } else {
                        date_picker_text.setText(date_from_date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // date_picker_text.setText(date_from_date);
                // VMSConstants.mysysout("comparedateis  "+todaydate + date_from_date);
               /* if (myCalendar.getTime().compareTo(new Date()) < 0) {
                    date_picker_text.setText("");
                    VMSConstants.mysysout("myCalendar.getTime() " + myCalendar.getTime(). + "  " + new Date());

                } else if (myCalendar.getTime().compareTo(new Date()) > 0 || myCalendar.getTime().compareTo(new Date()) == 0) {
                    date_picker_text.setText(date_from_date);

                }*/
            }

        };

        date_picker.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

                        date_picker_text_to.setText("Date");

                        MyCustomeToast.show(getActivity(), getResources().getString(R.string.msg_validdate), false, false);
                    } else {
                        date_picker_text_to.setText(date_too_date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        };

        date_picker_to.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date_to, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        chkAppointment = (CheckBox) view.findViewById(R.id.chkAppointment);
        linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
        dailyLinearLayout = (LinearLayout) view.findViewById(R.id.dailyLinearLayout);
        weeklyLinearLayout = (LinearLayout) view.findViewById(R.id.weeklyLinearLayout);
        monthlyLinearLayout = (LinearLayout) view.findViewById(R.id.monthlyLinearLayout);
        yearlyLinearLayout = (LinearLayout) view.findViewById(R.id.yearlyLinearLayout);

        chkAppointment.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chkAppointment.isChecked()) {
                    isRecurringChecked = true;
                    linearLayout1.setVisibility(View.VISIBLE);
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.GONE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);

                } else {
                    isRecurringChecked = false;
                    linearLayout1.setVisibility(View.GONE);
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.GONE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);

                }
            }
        });

        ArrayAdapter<String> nations_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                nations_array);
        nations_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIdType.setAdapter(nations_adapter);

        ArrayAdapter<String> days_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                days_array);
        days_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDaysType.setAdapter(days_adapter);
        spDaysType.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rec_every_days = parent.getItemAtPosition(position).toString();


                //{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> weekType_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                weekType_array);
        weekType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWeekType.setAdapter(weekType_adapter);
        spWeekType.setOnItemSelectedListener(new OnItemSelectedListener() {
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
        spWeekDays.setAdapter(weekDays_adapter);
        spWeekDays.setOnItemSelectedListener(new OnItemSelectedListener() {
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
        spOneToTwelve.setAdapter(oneToTwelve_adapter);
        spOneToTwelve.setOnItemSelectedListener(new OnItemSelectedListener() {
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
        spFirstToLast.setAdapter(firstToLast_adapter);
        spFirstToLast.setOnItemSelectedListener(new OnItemSelectedListener() {
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
        spSundayToSaturday.setAdapter(sundayToSaturday_adapter);
        spSundayToSaturday.setOnItemSelectedListener(new OnItemSelectedListener() {
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
        spJanToDec.setAdapter(janToDec_adapter);

        spJanToDec.setOnItemSelectedListener(new OnItemSelectedListener() {
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
        spIdType.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    rec_type = VMSConstants.Daily;
                    // LL Daily
                    dailyLinearLayout.setVisibility(View.VISIBLE);
                    weeklyLinearLayout.setVisibility(View.GONE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 2) {
                    rec_type = VMSConstants.Weekly;
                    // LL Weekly
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.VISIBLE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 3) {
                    rec_type = VMSConstants.Monthly;
                    // LL Monthly
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.GONE);

                    monthlyLinearLayout.setVisibility(View.VISIBLE);
                    yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 4) {
                    rec_type = VMSConstants.Yearly;
                    // LL Yearly
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.GONE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.VISIBLE);
                } else if (position == 0) {

                    // LL Daily
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.GONE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        /**
         * Check events
         */


        // tvNext = (TextView)findViewById(R.id.next);
        // tvNext.setOnClickListener(this);

       // iu = new VMSUtilities(getActivity());
       /* Bundle bundle = getArguments();


        sch_firstName = bundle.getString("sch_firstName");
        sch_middleName = bundle.getString("sch_middleName");
        sch_lastName = bundle.getString("sch_lastName");
        sch_employId = bundle.getString("sch_employId");
        sch_Designation = bundle.getString("sch_Designation");
        sch_companyName = bundle.getString("sch_companyName");
        sch_Category = bundle.getString("sch_Category");
        sch_dateOfBirth = bundle.getString("sch_dateOfBirth");
        sch_strSpnrNationality = bundle.getString("sch_strSpnrNationality");
        sch_workPermitNo = bundle.getString("sch_workPermitNo");
        sch_visitVisaNo = bundle.getString("sch_visitVisaNo");
        sch_idType = bundle.getString("sch_idType");
        sch_IdNo = bundle.getString("sch_IdNo");
        sch_mobileNo = bundle.getString("sch_mobileNo");
        sch_landlineNo = bundle.getString("sch_landlineNo");
        sch_sextype = bundle.getString("sch_sextype");
        sch2_firstName = bundle.getString("sch2_firstName");
        sch2_middleName = bundle.getString("sch2_middleName");

        sch2_mobNo = bundle.getString("sch2_mobNo");
        sch2_landlineNo = bundle.getString("sch2_landlineNo");

        sch2_eamil = bundle.getString("sch2_eamil");
        reasonforvisit = bundle.getString("sch2_reasonForVisit");

        sch2_othinfo1 = bundle.getString("sch2_othinfo1");
        sch2_othinfo2 = bundle.getString("sch2_othinfo2");

        sch2_deptName = bundle.getString("sch2_deptName");
        sch2_subDeptName = bundle.getString("sch2_subDeptName");*/
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String photopath = Prefs.getString("photopath", "nopath");
        if (!photopath.equalsIgnoreCase("nopath")) {
            File file = new File(photopath);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                profileImage.setImageBitmap(bitmap);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byteArrayImage = baos.toByteArray();
                byteArrayImageString = bitmapToBase64(bitmap);
                System.out.println("Appointment details " + byteArrayImage.toString());

            }

        }

        return view;
    }


    @OnCheckedChanged
            ({R.id.chkSunday, R.id.chkMonday, R.id.chkTuesday, R.id.chkWednesday, R.id.chkThursday, R.id.chkFriday, R.id.chkSaturday})
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

    @OnClick(R.id.uploadImage)
    void changeprofileimage_gallery(ImageView imageView) {

        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @OnClick(R.id.captureImage)
    void changeprofileimage_capture(ImageView captureImage) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 111);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111) {

            if (!data.equals(null)) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getActivity(), image);

                File finalFile = new File(getRealPathFromURI(tempUri));
                sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                //  Prefs.putString("photopath", finalFile.getAbsolutePath());

                //editor.commit();

                System.out.println("photo path :" + finalFile.getAbsolutePath());
                // profileImage.setImageBitmap(image);


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath(), options);

                profileImage.setImageBitmap(bitmap);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byteArrayImage = baos.toByteArray();
                byteArrayImageString = bitmapToBase64(bitmap);
                System.out.println("Appointment details in camera" + byteArrayImage);


            } else {
                Toast.makeText(getActivity(), "Please capture image", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == REQUEST_CODE && null != data) {

            final Uri uri = data.getData();
            Log.i("TAG", "Uri = " + uri.toString());
            try {
                // Get the file path from the URI
                final String path = FileUtils.getPath(getActivity(), uri);
                sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                File file = new File(path);
                if (file.exists()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    profileImage.setImageBitmap(bitmap);
                    byteArrayImageString = bitmapToBase64(bitmap);
                }
             /*   String imgString = Base64.encodeToString(getBytesFromBitmap(image),
                        Base64.NO_WRAP);*/
                // editor.putString("photopath", file.getAbsolutePath());


                //editor.commit();

            } catch (Exception e) {
                Log.e("FileSelectorTestActivity", "File select error", e);
            }

            //ivProfileImage.setImageURI(selectedImage);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    @OnClick(R.id.save)
    void Savemydata(TextView tv) {

     /*   Gson gson = new GsonBuilder().setDateFormat("E MMM dd HH:mm:ss Z yyyy").create();
        String date = "\"" + String.valueOf(sch_date_from) + "\"";

        System.out.println("date: before  " + date);

        Date test = gson.fromJson(date, Date.class);
        System.out.println("date: after " + test);
        java.sql.Date afterdateval = new java.sql.Date(test.getTime());
        System.out.println("afterdateval  " + afterdateval + "  " + afterdateval.getTime());

        // DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        DateFormat dateFormatNeeded = new SimpleDateFormat("dd MMM yyyy");
        try {
            String str_d = userDateFormat.format(afterdateval);
            Date date_ = userDateFormat.parse(str_d);
            String finaldate = dateFormatNeeded.format(date_);
            Date displaydate = dateFormatNeeded.parse(finaldate);

            java.sql.Date MysqlDate = new java.sql.Date(displaydate.getTime());
            System.out.println("*******************************" + "\n" + str_d + "\n" + date_ + "\n" + finaldate + "\n" + displaydate + "\n" + MysqlDate + "\n" + "***********************************");


        } catch (ParseException e) {
            e.printStackTrace();
        }


        sqlDate = new java.sql.Date(sch_date_from.getTime());
        sqlDateto = new java.sql.Date(sch_date_to.getTime());
        System.out.println(date_from + "  date boss  " + date_too + "\n" + " sqlDate  " + sqlDate);
        System.out.println("from date " + sch_date_from + "\n" + " todate " + sch_date_to);
        list.clear();
*/
    }

    @Override
    public void onClick(View v) {

        if (v.equals(tvSubmit)) {
            System.out.println(date_from + "  date boss  " + date_too);

            if (date_picker_text.getText().length() > 7 && date_picker_text_to.getText().length() > 7 && time_from.getText().length() > 0 && time_to.getText().length() > 0) {
                validator.validate();
            } else {
                VMSConstants.ShowSnackbar(getView(), getActivity().getString(R.string.selectdate));

            }


        } else if (v.equals(tvPrevious)) {
            getActivity().getFragmentManager().popBackStack();
        }

    }


    private void doinBackground() {
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


        SharedPreferences schSharedPreferences = getActivity().getSharedPreferences("SCH_PREFS", Context.MODE_PRIVATE);
        System.out.println("SCH_SCR1  " + schSharedPreferences.getString("SCH_SCR1", "novalue"));
        Gson sch1Gson = new Gson();
        Gson sch2Gson = new Gson();
        BookAnAppointment sch1BookAnAppointment = sch1Gson.fromJson(Prefs.getString("SCH_SCR1", "novalue"), BookAnAppointment.class);
        BookAnAppointment sch2BookAnAppointment = sch1Gson.fromJson(Prefs.getString("SCH_SCR2", "novalue"), BookAnAppointment.class);

        System.out.println(sch1BookAnAppointment.getVisitorFirstName() + " sch1BookAnAppointment " + sch1BookAnAppointment.getDOB());
        System.out.println(sch2BookAnAppointment.getVisitingDepartmentName() + " sch2BookAnAppointment " + sch2BookAnAppointment.getReasonForVisit());

/*
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String photopath = sharedPreferences.getString("photopath", "nopath");
        RequestBody body = null;
        System.out.println("photopath  " + photopath);
        if (!photopath.equalsIgnoreCase("nopath")) {
            File file = new File(photopath);
            System.out.println("File " + file + " File path " + file.getName());
            RequestBody photo = RequestBody.create(MediaType.parse("application/image"), file);
            body = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("photo", file.getName(), photo)
                    .build();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(photopath, options);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArrayImage = baos.toByteArray();

            System.out.println("byteArrayImage  " + byteArrayImage);
        }*/


        //sqlDate = new java.sql.Date(sch_date_from.getTime());
        // sqlDateto = new java.sql.Date(sch_date_to.getTime());
        // sharedPreferences.edit().clear().commit();
        PostClient postClient = new PostClient();
        final BookAnAppointment bookAnAppointment1 = new BookAnAppointment(sch1BookAnAppointment.getVisitorName(), sch1BookAnAppointment.getVisitorFirstName(), sch1BookAnAppointment.getVisitorMiddleName(), sch1BookAnAppointment.getVisitorLastName(),
                sch1BookAnAppointment.getTitle(), sch1BookAnAppointment.getEmployeeID(), sch1BookAnAppointment.getCompanyName(), sch1BookAnAppointment.getDesignation(), sch1BookAnAppointment.getNationality(), sch1BookAnAppointment.getSectorCompCategory(),
                sch1BookAnAppointment.getDOB(), sch1BookAnAppointment.getGender(), sch1BookAnAppointment.getWorkPermitNumber(), sch1BookAnAppointment.getVisitVisaNumber(), sch1BookAnAppointment.getMobileNumber(), sch1BookAnAppointment.getLandlinePhoneNumber(),
                sch1BookAnAppointment.getNationalIDType(), sch1BookAnAppointment.getNationalID(), sch2BookAnAppointment.getContactPersonEmpID(), sch2BookAnAppointment.getContactPersonName(), sch2BookAnAppointment.getVisitingDepartmentName(),
                sch2BookAnAppointment.getVisitingSubDepartmentName(), sch2BookAnAppointment.getContactNumber(), sch2BookAnAppointment.getContactPersonEmailId(), sch2BookAnAppointment.getReasonForVisit(), sch2BookAnAppointment.getPersonalBelongings(),
                sch2BookAnAppointment.getMeetingPrerequisite(), isRecurringChecked, String.valueOf(rec_type), RecurringInput, date_from_date, date_too_date, time_froom, time_too, totaldays.getText().toString(), totalhrs.getText().toString(), byteArrayImageString);
        int val = Integer.parseInt(Prefs.getString("userRoleId", "0"));
        String token = "GuestUser";
        if (val > 0) {
            token = Prefs.getString("access_token", "");
        }


        Call<BookAnAppointment> bookAnAppointmentCall = postClient.myApiEndpointInterface.createAnAppointment(bookAnAppointment1, token);
        System.out.println("values " + bookAnAppointment1.getGender() + " \n" + bookAnAppointment1.getRecurringInput() + " \n" +
                bookAnAppointment1.getStartDate() + "\n " + bookAnAppointment1.getEndDate() + "\n " + bookAnAppointment1.getStartTime() + "\n " + bookAnAppointment1.getEndTime());


        bookAnAppointmentCall.enqueue(new Callback<BookAnAppointment>() {
            @Override
            public void onResponse(Response<BookAnAppointment> response, Retrofit retrofit) {
                BookAnAppointment bookAnAppointment1 = response.body();
                Gson gson = new Gson();

                int statuscode = response.code();

                if (response.isSuccess() == true) {
                    System.out.println("statuscode " + getClass().getName() + " \n " + "response.isSuccess() " + response.isSuccess() + "\n" + response.errorBody() + "\n" + response.raw() + "\n" + gson.toJson(bookAnAppointment1));


                    Toast.makeText(getActivity().getApplicationContext(), "Appointmet created ", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("photopath");
                    editor.apply();


                    // getActivity().finish();
                    if (Prefs.getString("UserId", "").equalsIgnoreCase("")) {
                        Intent intent = new Intent(getActivity(), VMSMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), GDMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }

                } else {
                    Toast.makeText(getActivity(), "Appointment was not booked !!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), VMSMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Gson gson = new Gson();

                System.out.println("Failure called   " + t.toString() + "\n" + gson.toJson(bookAnAppointment1));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Failed ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // TODO Auto-generated method stub
//		String server_mobile_response = iu.submitRegisterUserrequest(strFirstName, strMiddleName, strLastName, strEmployId, strDesignation,strCompanyName,strSelectCategory,
//				strDOB,strNationality,strMobileNum,strLandLineNum,strGender,strWorkPermitNum,strVisitVisaNum,strIdType,strIdNum,strEmployId,strEmployName,
//				strDeptName,strSubDeptName,strEmpMobNum,strEmpLandNum,strEmpEmail,strReasonForVisit,strPersonalBeongings,strPrequisites,strStartDate,strStartTime,
//				strEndDate,strEndTime);

/*        String server_mobile_response = iu.submitRegisterUserrequest("Nishanth", "", "Kumar", "FT1001", "SE", "FT", "",
                "25-25-25", "India", "9989898898", "9989898898", "M", "23ee4", "44tt", "tttt", "5555", "Ft123", "tf44",
                "yuyu", "uuuu", "45678903344", "45678903344", "nishanth@gmail.com", "ggg", "jhjhjh", "", "21/02/2015", "14:50",
                "25/02/2015", "16.50");

        Log.i("VMS", "response : " + server_mobile_response);
        Toast.makeText(getActivity(), server_mobile_response.toString(), Toast.LENGTH_LONG).show();*/

    }

    @Override
    public void onValidationSucceeded() {
        doinBackground();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {





      /*  for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);


            }

            else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }*/
    }


    @OnClick(R.id.back)
    public void backevent(ImageView back) {
        getActivity().getFragmentManager().popBackStack();
    }
}
