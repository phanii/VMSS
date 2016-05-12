package com.visitormanagementsystem;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ApproveOrRejectByEmploy extends Fragment implements OnClickListener {
    static final int TIME_DIALOG_ID = 1111;
    ImageView sliderpanel_image, back;
    TextView tvGoBack, tvApprove, tvReject;
    Fragment imr;
    Bundle bundle;
    String nations_array[] = {"--Select--", "Daily", "Weekly", "Monthly", "Yearly"};
    String days_array[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String weekType_array[] = {"first", "second", "third", "fourth", "last"};
    String weekDays_array[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String oneToTwelve_array[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String firstToLast_array[] = {"first", "second", "third", "fourth", "last"};
    String sundayToSaturday_array[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String janToDec_array[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    Spinner spIdType, spDaysType, spWeekType, spWeekDays, spOneToTwelve, spFirstToLast, spSundayToSaturday, spJanToDec;
    TextView tvNext, tvHome, date_picker_text, date_picker_text_to;
    LinearLayout linearLayout1, dailyLinearLayout, weeklyLinearLayout, monthlyLinearLayout, yearlyLinearLayout;
    ImageView date_picker, date_picker_to;
    EditText time_from, time_to;
    private CheckBox chkAppointment;
    private int hour;
    private int minute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.approve_reject, null);


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

        time_from = (EditText) view.findViewById(R.id.time_from);
        time_to = (EditText) view.findViewById(R.id.time_to);

        back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(this);
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
                    }
                }, hour, minute, true);//Yes 24 hour time
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
                        time_to.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
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

                date_picker_text.setText(sdf.format(myCalendar.getTime()));
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

                date_picker_text_to.setText(sdf.format(myCalendar.getTime()));
            }

        };

        date_picker_to.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date_to, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        chkAppointment = (CheckBox) view.findViewById(R.id.chkAppointment);
        linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
        dailyLinearLayout = (LinearLayout) view.findViewById(R.id.dailyLinearLayout);
        weeklyLinearLayout = (LinearLayout) view.findViewById(R.id.weeklyLinearLayout);
        monthlyLinearLayout = (LinearLayout) view.findViewById(R.id.monthlyLinearLayout);
        yearlyLinearLayout = (LinearLayout) view.findViewById(R.id.yearlyLinearLayout);

        sliderpanel_image = (ImageView) view.findViewById(R.id.sliderpanel_image);
        sliderpanel_image.setOnClickListener(this);

        chkAppointment.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chkAppointment.isChecked()) {
                    linearLayout1.setVisibility(View.VISIBLE);
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.GONE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);

                } else {
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

        ArrayAdapter<String> days_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion, days_array);
        days_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDaysType.setAdapter(days_adapter);

        ArrayAdapter<String> weekType_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                weekType_array);
        weekType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWeekType.setAdapter(weekType_adapter);

        ArrayAdapter<String> weekDays_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                weekDays_array);
        weekDays_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWeekDays.setAdapter(weekDays_adapter);

        ArrayAdapter<String> oneToTwelve_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                oneToTwelve_array);
        oneToTwelve_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOneToTwelve.setAdapter(oneToTwelve_adapter);

        ArrayAdapter<String> firstToLast_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                firstToLast_array);
        firstToLast_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFirstToLast.setAdapter(firstToLast_adapter);

        ArrayAdapter<String> sundayToSaturday_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                sundayToSaturday_array);
        sundayToSaturday_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSundayToSaturday.setAdapter(sundayToSaturday_adapter);

        ArrayAdapter<String> janToDec_adapter = new ArrayAdapter<String>(getActivity(), R.layout.sp_selectquestion,
                janToDec_array);
        janToDec_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJanToDec.setAdapter(janToDec_adapter);
        spIdType.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    // LL Daily
                    dailyLinearLayout.setVisibility(View.VISIBLE);
                    weeklyLinearLayout.setVisibility(View.GONE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 2) {
                    // LL Weekly
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.VISIBLE);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 3) {
                    // LL Monthly
                    dailyLinearLayout.setVisibility(View.GONE);
                    weeklyLinearLayout.setVisibility(View.GONE);

                    monthlyLinearLayout.setVisibility(View.VISIBLE);
                    yearlyLinearLayout.setVisibility(View.GONE);
                } else if (position == 4) {
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

        tvGoBack = (TextView) view.findViewById(R.id.goBack);
        tvApprove = (TextView) view.findViewById(R.id.approve);
        tvReject = (TextView) view.findViewById(R.id.reject);
        tvGoBack.setOnClickListener(this);
        tvApprove.setOnClickListener(this);
        tvReject.setOnClickListener(this);

        bundle = new Bundle();

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
                bundle.putString("servicename", "To Be Approved");
                imr.setArguments(bundle);
            }
        } else if (v.equals(tvApprove)) {
            Toast.makeText(getActivity(), "Appointment is Approved", Toast.LENGTH_LONG).show();
        } else if (v.equals(tvReject)) {
            Toast.makeText(getActivity(), "Appointment is Rejected", Toast.LENGTH_LONG).show();
        } else if (v.equals(back)) {
            getActivity().getFragmentManager().popBackStack();
        }
    }
}
